package com.vmzone.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import com.vmzone.demo.enums.Gender;
import com.vmzone.demo.models.Category;
import com.vmzone.demo.models.Product;
import com.vmzone.demo.models.ShoppingCartItem;
import com.vmzone.demo.repository.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vmzone.demo.dto.ChangePasswordDTO;
import com.vmzone.demo.dto.EditProfileDTO;
import com.vmzone.demo.dto.LoginDTO;
import com.vmzone.demo.dto.RegisterDTO;
import com.vmzone.demo.dto.ShoppingCartItemDTO;
import com.vmzone.demo.exceptions.BadCredentialsException;
import com.vmzone.demo.exceptions.InvalidEmailException;
import com.vmzone.demo.exceptions.ResourceAlreadyExistsException;
import com.vmzone.demo.exceptions.ResourceDoesntExistException;
import com.vmzone.demo.models.User;
import com.vmzone.demo.repository.UserRepository;
import com.vmzone.demo.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class UserServiceTests {
	private static final String PASSWORD = "1234";

	private static final String EMAIL = "example@abv.bg";

	private static final EditProfileDTO EDITED_USER_DTO = new EditProfileDTO("EditName", "EditSurname", EMAIL, "Male",
			1, "1234", "Sofia", "12345", "EditAdress", 25);

	private static User EXPECTED_USER;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder bCryptPasswordEncoder;

	@Mock
    private ShoppingCartRepository shoppingCartRepository;

	@InjectMocks
	private UserService userService;

	@Before
	public void init() {
		EXPECTED_USER = new User("Ivan", "Ivanov", EMAIL, PASSWORD, Gender.Female, 0,  null, null, null, null, 25, false);
	}

	@Test(expected = ResourceAlreadyExistsException.class)
	public void testRegisterUserWithAlreadyExistentUser() throws AddressException, ResourceAlreadyExistsException,
			InvalidEmailException, SQLException, MessagingException, IOException {
		when(userRepository.findByEmail(EMAIL)).thenReturn(new User());
		userService.register(new RegisterDTO( "Ivan", "Ivanov", EMAIL, PASSWORD, "Male", 0));
	}

	@Test
	public void testRegisterUserWithNonExistingUser() throws AddressException, ResourceAlreadyExistsException,
			InvalidEmailException, SQLException, MessagingException, IOException {
		when(userRepository.findByEmail(EMAIL)).thenReturn(null);
		when(bCryptPasswordEncoder.encode(PASSWORD)).thenReturn(PASSWORD);
		userService.register(new RegisterDTO("Ivan", "Ivanov", EMAIL, PASSWORD, "Male", 0));
	}

	@Test(expected = ResourceDoesntExistException.class)
	public void testLoginUserWithNonExistentUser() throws BadCredentialsException, ResourceDoesntExistException {
		when(userRepository.findByEmail(EMAIL)).thenReturn(null);
		userService.login(new LoginDTO(EMAIL, PASSWORD));
	}

	@Test(expected = BadCredentialsException.class)
	public void testLoginUserWithWrongPasswordInput() throws BadCredentialsException, ResourceDoesntExistException {
		when(userRepository.findByEmail(EMAIL)).thenReturn(EXPECTED_USER);
		userService.login(new LoginDTO(EMAIL, "wrongPassword"));
	}

	@Test
	public void testLoginUserWithCorrectInput() throws ResourceDoesntExistException, BadCredentialsException {
		when(userRepository.findByEmail(EMAIL)).thenReturn(EXPECTED_USER);
		when(bCryptPasswordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);
		User u = userService.login(new LoginDTO(EMAIL, PASSWORD));
		assertEquals(u, EXPECTED_USER);
	}

	@Test(expected = ResourceDoesntExistException.class)
	public void testEditProfileWithNonExistingUser() throws ResourceDoesntExistException, ResourceAlreadyExistsException {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());
		userService.editProfile(1L, EDITED_USER_DTO);
	}

	@Test
	public void testEditProfileWithCorrectInput() throws ResourceDoesntExistException, ResourceAlreadyExistsException {
		when(userRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_USER));
		User user = userService.editProfile(1L, EDITED_USER_DTO);

		assertEquals(user.getName(), EDITED_USER_DTO.getName());
		assertEquals(user.getSurname(), EDITED_USER_DTO.getSurname());
		assertEquals(user.getEmail(), EDITED_USER_DTO.getEmail());
		assertEquals(user.getGender().name(), EDITED_USER_DTO.getGender());
		assertEquals(user.getIsSubscribed(), EDITED_USER_DTO.getIsSubscribed());
		assertEquals(user.getPhone(), EDITED_USER_DTO.getPhone());
		assertEquals(user.getCity(), EDITED_USER_DTO.getCity());
		assertEquals(user.getPostCode(), EDITED_USER_DTO.getPostCode());
		assertEquals(user.getAdress(), EDITED_USER_DTO.getAdress());
		assertEquals(user.getAge(), EDITED_USER_DTO.getAge());
	}

	@Test(expected = ResourceDoesntExistException.class)
	public void testChangePasswordWithInvalidUserId() throws ResourceDoesntExistException {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());
		userService.changePassword(1L, new ChangePasswordDTO("12345"));
	}

	@Test
	public void testChangePasswordWithCorrectData() throws ResourceDoesntExistException {
		final String newPassword = "12345";
		when(userRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_USER));
		when(bCryptPasswordEncoder.encode(newPassword)).thenReturn(newPassword);
		userService.changePassword(1L, new ChangePasswordDTO(newPassword));
		assertEquals(EXPECTED_USER.getPassword(), newPassword);
	}

	@Test(expected = ResourceDoesntExistException.class)
	public void testForgottenPasswordWhenUserDoesntExist() throws AddressException, ResourceDoesntExistException,
			InvalidEmailException, MessagingException, IOException {
		when(userRepository.findByEmail(EMAIL)).thenReturn(null);
		userService.forgottenPassword(EMAIL);
	}

	@Test(expected = ResourceDoesntExistException.class)
	public void testRemoveUserByIdWhenUserDoesntExist() throws ResourceDoesntExistException {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());
		userService.removeUserById(1L);
	}

	@Test
	public void testRemoveUserByIdWithCorrectInput() throws ResourceDoesntExistException {
		when(userRepository.findById(1L)).thenReturn(Optional.of(EXPECTED_USER));
		userService.removeUserById(1L);
		assertTrue(EXPECTED_USER.isDeleted());
	}

	@Test
	public void testGetShoppingCart() {
		List<ShoppingCartItemDTO> expectedResult= new ArrayList<>();
		expectedResult.add(new ShoppingCartItemDTO(1L, "Product 1", 2.0, 5));
		expectedResult.add(new ShoppingCartItemDTO(2L, "Product 2", 3.0, 6));

		List<ShoppingCartItem> cartItems = new ArrayList<>();

		cartItems.add(new ShoppingCartItem( 1L, new Product(1L,null,"Product 1","Ïnfo",2,1,24,
                LocalDateTime.of( 2019,1,1,1,1), 10,false,1,"No info",3.00),new User(),5));
        cartItems.add(new ShoppingCartItem( 2L, new Product(2L,null,"Product 2","Ïnfo",3,1,24,
                LocalDateTime.of( 2019,1,1,1,1), 10,false,1,"No info",3.00),new User(),6));

        when(shoppingCartRepository.findByUser(EXPECTED_USER)).thenReturn(cartItems);

		boolean areEqual = true;
		List<ShoppingCartItemDTO> items=this.userService.getShoppingCart(EXPECTED_USER);
		for(int i =0;i<expectedResult.size();i++) {
            System.out.println(expectedResult.get(i));
            System.out.println(items.get(i));
			if(!expectedResult.get(i).equals(items.get(i))) {
				areEqual = false;
				break;
			}
		}

		assertTrue(areEqual);
	}

	@Configuration
	static class Config {

	}

}
