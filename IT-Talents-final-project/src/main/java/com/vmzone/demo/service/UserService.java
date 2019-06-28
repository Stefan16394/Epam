package com.vmzone.demo.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.vmzone.demo.enums.Gender;
import com.vmzone.demo.models.ShoppingCartItem;
import com.vmzone.demo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vmzone.demo.dto.CartProductDTO;
import com.vmzone.demo.dto.ChangePasswordDTO;
import com.vmzone.demo.dto.ContactUsDTO;
import com.vmzone.demo.dto.EditProfileDTO;
import com.vmzone.demo.dto.LoginDTO;
import com.vmzone.demo.dto.RegisterDTO;
import com.vmzone.demo.dto.ShoppingCartItemDTO;
import com.vmzone.demo.exceptions.BadCredentialsException;
import com.vmzone.demo.exceptions.InvalidEmailException;
import com.vmzone.demo.exceptions.NotEnoughQuantityException;
import com.vmzone.demo.exceptions.ResourceAlreadyExistsException;
import com.vmzone.demo.exceptions.ResourceDoesntExistException;
import com.vmzone.demo.models.Product;
import com.vmzone.demo.models.User;
import com.vmzone.demo.repository.ProductRepository;
import com.vmzone.demo.repository.UserRepository;
import com.vmzone.demo.utils.EmailSender;
import com.vmzone.demo.utils.PasswordGenerator;
import com.vmzone.demo.utils.RegexValidator;

/**
 * Service layer communicating with user repository and product repository for managing user requests
 *
 * @author Stefan Rangelov and Sabiha Djurina
 */

@Service
public class UserService {
    private static final int LENGTH_FOR_FORGOTTEN_PASSWORD = 8;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User register(RegisterDTO user) throws SQLException, ResourceAlreadyExistsException, AddressException,
            InvalidEmailException, MessagingException, IOException {
        User u = this.userRepository.findByEmail(user.getEmail());
        if (u != null) {
            throw new ResourceAlreadyExistsException(
                    "There is already an account with this email address " + u.getEmail());
        }
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        User newUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(),
                hashedPassword, Gender.valueOf(user.getGender()), user.getIsSubscribed(), null, null, null, null, 0, false);
        EmailSender.registration(user.getEmail());

         return this.userRepository.save(newUser);
    }

    public User login(LoginDTO loginDTO) throws ResourceDoesntExistException, BadCredentialsException {
        User user = this.userRepository.findByEmail(loginDTO.getEmail());
        if (user == null) {
            throw new ResourceDoesntExistException( "User doesn't exist");
        }
        boolean passwordsMatch = bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword());
        if (!passwordsMatch) {
            throw new BadCredentialsException( "Incorrect email or password");
        }
        return user;
    }

    /**
     * Edit profile of user
     *
     * @param id   - id of user object stored in db
     * @param user - EditProfileDTO user information for editing profile
     * @return User - the user with the edited profile
     * @throws ResourceDoesntExistException   - when the user has been deleted or does not exist in DB
     * @throws ResourceAlreadyExistsException - when the edited email already exist in DB and its to another user
     */

    public User editProfile(long id, EditProfileDTO user)
            throws ResourceDoesntExistException, ResourceAlreadyExistsException {
        User u = null;
        try {
            u = this.userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceDoesntExistException( "User doesn't exist");
        }
        if (u.isDeleted()) {
            throw new ResourceDoesntExistException( "User has been deleted");
        }
        User check = this.userRepository.findByEmail(user.getEmail());

        if (check != null && !u.equals(check)) {
            throw new ResourceAlreadyExistsException( "There is already a user with this email!");
        }

        u.setName(user.getName());
        u.setSurname(user.getSurname());
        u.setEmail(user.getEmail());
        u.setGender(Gender.valueOf(user.getGender()));
        u.setIsSubscribed(user.getIsSubscribed());
        u.setPhone(user.getPhone());
        u.setCity(user.getCity());
        u.setPostCode(user.getPostCode());
        u.setAdress(user.getAdress());
        u.setAge(user.getAge());

        this.userRepository.save(u);
        return u;
    }

    public void changePassword(long id, ChangePasswordDTO pass) throws ResourceDoesntExistException {
        User u = null;
        try {
            u = this.userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceDoesntExistException( "User doesn't exist");
        }
        if (u.isDeleted()) {
            throw new ResourceDoesntExistException( "User has been deleted");
        }

        String hashedPassword = bCryptPasswordEncoder.encode(pass.getPassword());
        u.setPassword(hashedPassword);
        this.userRepository.save(u);
    }

    /**
     * Send forgotten password to email
     *
     * @param email - email to send the forgotten password to
     * @throws ResourceDoesntExistException - when the user with this email does not exist in DB
     * @throws AddressException
     * @throws InvalidEmailException
     * @throws MessagingException
     * @throws IOException
     */

    public void forgottenPassword(String email) throws ResourceDoesntExistException, AddressException,
            InvalidEmailException, MessagingException, IOException {
        User u = this.userRepository.findByEmail(email);

        if (u == null || u.isDeleted()) {
            throw new ResourceDoesntExistException( "User doesn't exist");
        }

        String newPass = PasswordGenerator.makePassword(LENGTH_FOR_FORGOTTEN_PASSWORD);
        String hashedPassword = bCryptPasswordEncoder.encode(newPass);
        EmailSender.forgottenPassword(u.getEmail(), newPass);
        u.setPassword(hashedPassword);
        this.userRepository.save(u);
    }

    @Scheduled(fixedRate = 7 * 24 * 60 * 60000)
    public void sendSubscribed() throws AddressException, InvalidEmailException, MessagingException, IOException {
        List<String> emails = this.userRepository.findAll().stream().filter(user -> user.getIsSubscribed() == 1)
                .map(user -> user.getEmail()).collect(Collectors.toList());

        EmailSender.sendSubscripedPromotions(emails);

    }

    public void removeUserById(long id) throws ResourceDoesntExistException {
        User u = null;
        try {
            u = this.userRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResourceDoesntExistException( "User doesn't exist");
        }
        if (u.isDeleted()) {
            throw new ResourceDoesntExistException( "User has been deleted");
        }
        u.setDeleted(true);
        this.userRepository.save(u);

    }

    public void contactUs(ContactUsDTO contact)
            throws InvalidEmailException, AddressException, MessagingException, IOException {
        if (!RegexValidator.validateEmail(contact.getEmail())) {
            throw new InvalidEmailException( "Incorrect email or password");
        }

        EmailSender.contactUs(contact.toString());
    }

    public List<ShoppingCartItemDTO> getShoppingCart(User user) {
        List<ShoppingCartItemDTO> shoppingCartItemDTOS = new ArrayList<>();
        List<ShoppingCartItem> cartItems = this.shoppingCartRepository.findByUser(user);
        for (ShoppingCartItem item : cartItems) {
            Product product = item.getProduct();
            shoppingCartItemDTOS.add(new ShoppingCartItemDTO(product.getProductId(), product.getTitle(),
            product.getPrice(), item.getQuantity()));
        }
        return shoppingCartItemDTOS;
    }

    /**
     * Add product in users cart
     *
     * @param addProduct - dto with information for the product
     * @param id         - id of product object stored in db
     * @return id -  id of product added to cart
     * @throws NotEnoughQuantityException   - when there is not enough quantity of the added product
     * @throws ResourceDoesntExistException - when the product has been deleted or does not exist in db
     */

    public long addProductToCart(CartProductDTO addProduct, User user) throws NotEnoughQuantityException, ResourceDoesntExistException {
        final Product p;
        try {
            p = this.productRepository.findById(addProduct.getProductId()).get();
        } catch (NoSuchElementException e) {
            throw new ResourceDoesntExistException( "Product doesn't exist");
        }
        if (p.getQuantity() < addProduct.getQuantity()) {
            throw new NotEnoughQuantityException(
                    "There is not enough quantity of this product! Try with less or add it to you cart later.");
        }
        if (p.isDeleted()) {
            throw new ResourceDoesntExistException( "Product has been deleted");
        }

        this.shoppingCartRepository.save(new ShoppingCartItem(p, user, addProduct.getQuantity()));

        return addProduct.getProductId();
    }

    /**
     * Edit product in users cart
     *
     * @param editProduct - dto with info for editing product in the cart
     * @param id          - id of product object stored in db
     * @return id - id of product object updated in cart
     * @throws NotEnoughQuantityException   - when there is not enough quantity if the added product
     * @throws ResourceDoesntExistException - when the product has been deleted or does not exist in db
     */

    public long updateProductInCart(CartProductDTO editProduct, User user) throws NotEnoughQuantityException, ResourceDoesntExistException {
        final Product p;
        try {
            p = this.productRepository.findById(editProduct.getProductId()).get();
        } catch (NoSuchElementException e) {
            throw new ResourceDoesntExistException( "Product doesn't exist");
        }
        List<ShoppingCartItem> items = this.shoppingCartRepository.findByUser(user);
        boolean isInShoppingCart = items.stream().anyMatch(i -> i.getProduct().getProductId().equals(p.getProductId()));

        if (!isInShoppingCart) {
            throw new ResourceDoesntExistException( "Product doesn't exist in your cart.");
        }
        if (p.getQuantity() < editProduct.getQuantity()) {
            throw new NotEnoughQuantityException(
                    "There is not enough quantity of this product! Try with less or add it to you cart later.");
        }
        if (p.isDeleted()) {
            throw new ResourceDoesntExistException("Product has been deleted");
        }
        // TO DO
        ShoppingCartItem item = items.stream().filter(i->i.getProduct().getProductId().equals(editProduct.getProductId())).findFirst().get();
        item.setQuantity(editProduct.getQuantity());
        this.shoppingCartRepository.save(item);
        return editProduct.getProductId();
    }

    public void deleteProductInCart(long productId, User user) {
        this.shoppingCartRepository.deleteByUserAndProductProductId(user, productId);
    }
}
