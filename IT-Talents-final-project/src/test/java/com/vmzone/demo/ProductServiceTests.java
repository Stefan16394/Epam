package com.vmzone.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.vmzone.demo.dto.AddCharacteristicDTO;
import com.vmzone.demo.exceptions.ResourceAlreadyExistsException;
import com.vmzone.demo.models.*;
import com.vmzone.demo.repository.CharacteristicsRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import com.vmzone.demo.dto.AddProductDTO;
import com.vmzone.demo.dto.EditProductDTO;
import com.vmzone.demo.dto.ListReview;
import com.vmzone.demo.exceptions.ResourceDoesntExistException;
import com.vmzone.demo.repository.CategoryRepository;
import com.vmzone.demo.repository.ProductRepository;
import com.vmzone.demo.repository.ReviewRepository;
import com.vmzone.demo.service.CategoryService;
import com.vmzone.demo.service.ProductService;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class ProductServiceTests {

    private static final long DEFAULT_ID_TO_SEARCH = 1L;

    private static final AddProductDTO ADD_PRODUCT_DTO = new AddProductDTO(1L, "Product", "Information", 1, 24, 20, 0,
            "detailed info");

    private static final Product TEST_PRODUCT = new Product(1L, new Category("Shoes", null), "Product", "Information",
            4.00, 1, 23, LocalDateTime.now(), 20, true, 1, "detailed info", 4.0);
    private static final AddCharacteristicDTO ADD_CHARACTERISTIC_DTO = new AddCharacteristicDTO("color", "red");

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CharacteristicsRepository characteristicsRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ProductService productService;

    @Test(expected = ResourceDoesntExistException.class)
    public void testAddProductWhenCategoryDoesntExist() throws ResourceDoesntExistException {
        when(categoryRepository.findById(ADD_PRODUCT_DTO.getCategoryId())).thenReturn(Optional.empty());
        productService.addProduct(ADD_PRODUCT_DTO);
    }

    @Test
    public void testAddProductWithValidInput() throws ResourceDoesntExistException {
        when(categoryRepository.findById(ADD_PRODUCT_DTO.getCategoryId()))
                .thenReturn(Optional.of(new Category(1L, "Category", null)));
        productService.addProduct(ADD_PRODUCT_DTO);
    }

    @Test
    public void testGetReviewsForProduct() {
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review(1L, new Product(), new User(), "Review1", 4, LocalDateTime.now(), false));
        reviews.add(new Review(2L, new Product(), new User(), "Review2", 2, LocalDateTime.now(), false));
        reviews.add(new Review(3L, new Product(), new User(), "Review3", 5, LocalDateTime.now(), false));

        when(reviewRepository.findByReviewIdAndIsDeletedIsFalse(DEFAULT_ID_TO_SEARCH)).thenReturn(reviews);
        List<ListReview> result = productService.getReviewsForProduct(DEFAULT_ID_TO_SEARCH);
        assertEquals(reviews.size(), result.size());
    }

    @Test(expected = ResourceDoesntExistException.class)
    public void testRemoveProductByIdWhenProductDoesntExist() throws ResourceDoesntExistException {
        when(productRepository.findById(DEFAULT_ID_TO_SEARCH)).thenReturn(Optional.empty());
        productService.removeProductById(DEFAULT_ID_TO_SEARCH);
    }

    @Test
    public void testRemoveProductByIdWhenProductExist() throws ResourceDoesntExistException {
        when(productRepository.findById(DEFAULT_ID_TO_SEARCH)).thenReturn(Optional.of(TEST_PRODUCT));
        productService.removeProductById(DEFAULT_ID_TO_SEARCH);

        assertTrue(TEST_PRODUCT.isDeleted());
    }

    @Test(expected = ResourceDoesntExistException.class)
    public void testEditProductWhenProductDoesntExist() throws ResourceDoesntExistException {
        EditProductDTO edit = new EditProductDTO(1L, "editedTitle", "editedInfo", 1, 20, 23, false, 1, "editedInfo");
        when(productRepository.findById(DEFAULT_ID_TO_SEARCH)).thenReturn(Optional.empty());
        productService.editProduct(DEFAULT_ID_TO_SEARCH, edit);
    }

    @Test
    public void testEditProductWithCorrectInput() throws ResourceDoesntExistException {
        EditProductDTO edit = new EditProductDTO(DEFAULT_ID_TO_SEARCH, "editedTitle", "editedInfo", 1, 20, 23, false, 1, "editedInfo");
        when(productRepository.findById(DEFAULT_ID_TO_SEARCH)).thenReturn(Optional.of(TEST_PRODUCT));
        when(categoryRepository.findById(DEFAULT_ID_TO_SEARCH)).thenReturn(Optional.of(new Category(1L, "Shoes", null)));
        productService.editProduct(DEFAULT_ID_TO_SEARCH, edit);

        assertEquals(edit.getCategoryId(), TEST_PRODUCT.getCategory().getCategoryId());
        assertEquals(edit.getDelivery(), TEST_PRODUCT.getDelivery());
        assertEquals(edit.getDetailedInformation(), TEST_PRODUCT.getDetailedInformation());
        assertEquals(edit.getInformation(), TEST_PRODUCT.getInformation());
        assertEquals(edit.getInSale(), TEST_PRODUCT.getInSale());
        assertEquals(edit.getInStock(), TEST_PRODUCT.getInStock());
        assertEquals(edit.isDeleted(), TEST_PRODUCT.isDeleted());
        assertEquals(edit.getQuantity(), TEST_PRODUCT.getQuantity());
        assertEquals(edit.getTitle(), TEST_PRODUCT.getTitle());
    }

    @Test(expected = ResourceAlreadyExistsException.class)
    public void addCharacteristicForProductWhenCharacteristicExists() throws ResourceDoesntExistException, ResourceAlreadyExistsException {
        Mockito.when(characteristicsRepository.findNameOfCharacteristicForProduct(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(new Characteristic());
        productService.addCharacteristicForProduct(1L, ADD_CHARACTERISTIC_DTO);
    }

    @Test(expected = ResourceDoesntExistException.class)
    public void addCharacteristicForProductWhenProductDoesntExists() throws ResourceDoesntExistException, ResourceAlreadyExistsException {
        Mockito.when(characteristicsRepository.findNameOfCharacteristicForProduct(Mockito.anyLong(), Mockito.anyString()))
                .thenReturn(null);
        Mockito.when(this.productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        productService.addCharacteristicForProduct(1L, ADD_CHARACTERISTIC_DTO);
    }

    @Test
    public void getCharacteristicsForProduct() {
        List<Characteristic> list = new ArrayList<>();
        list.add(new Characteristic(1L,TEST_PRODUCT, "color", "red",false));
        list.add(new Characteristic(2L,TEST_PRODUCT, "size", "L",false));
        list.add(new Characteristic(3l,new Product(2L, new Category("Shoes", null), "Product", "Information",
                4.00, 1, 23, LocalDateTime.now(), 20, true, 1, "detailed info", 4.0), "size", "M",false));
        Mockito.when(this.characteristicsRepository.findAll())
                .thenReturn(list);
        List<AddCharacteristicDTO> result = productService.getCharacteristicsForProduct(1L);
        Assert.assertEquals(2, result.size());
    }


    @Configuration
    static class Config {

    }
}
