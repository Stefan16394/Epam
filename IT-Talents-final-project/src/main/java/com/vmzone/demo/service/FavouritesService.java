package com.vmzone.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vmzone.demo.dto.AddToFavouritesDTO;
import com.vmzone.demo.dto.ListFavouriteProductDTO;
import com.vmzone.demo.exceptions.ResourceDoesntExistException;
import com.vmzone.demo.models.Favourite;
import com.vmzone.demo.repository.FavouritesRepository;
import com.vmzone.demo.repository.ProductRepository;
import com.vmzone.demo.repository.UserRepository;

/**
 * Service layer communicating with favourites repository for managing favourites requests
 *
 * @author Stefan Rangelov and Sabiha Djurina
 */
@Service
public class FavouritesService {

    @Autowired
    private FavouritesRepository favouritesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Favourite addToFavourites(AddToFavouritesDTO fav, long id) throws ResourceDoesntExistException {
        try {
            Favourite newFav = new Favourite(
                    this.productRepository.findById(fav.getProductId()).get(),
                    this.userRepository.findById(id).get());
            return this.favouritesRepository.save(newFav);
        } catch (NoSuchElementException e) {
            throw new ResourceDoesntExistException( "Product or user doesnt exist.");
        }
    }

    /**
     * remove favourite product for user
     *
     * @param id     - id of product object stored in db
     * @param userId - id of user objest stored in db
     * @throws ResourceDoesntExistException - when the favourite does not exist in db or has been deleted
     */

    public void removeFavouriteById(long id, long userId) throws ResourceDoesntExistException {

        Favourite fav = this.favouritesRepository.findByUserUserIdAndFavouritesId(userId, id);

        if (fav == null) {
            throw new ResourceDoesntExistException( "Favourite doesn't exist");
        }
        if (fav.isDeleted() ) {
            throw new ResourceDoesntExistException( "Favourite has already been deleted");
        }
        fav.setDeleted(true);
        this.favouritesRepository.save(fav);
    }

    public List<ListFavouriteProductDTO> getFavouritesForUser(long id) {
        return this.favouritesRepository.findByUserUserId(id).stream()
                .filter(fav -> !fav.isDeleted())
                .map(fav -> new ListFavouriteProductDTO(fav.getProduct().getTitle(), fav.getProduct().getInformation(),
                        fav.getProduct().getRating()))
                .collect(Collectors.toList());
    }
}
