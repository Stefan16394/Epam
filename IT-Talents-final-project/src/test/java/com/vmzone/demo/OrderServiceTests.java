package com.vmzone.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vmzone.demo.enums.Gender;
import com.vmzone.demo.models.*;
import com.vmzone.demo.repository.ShoppingCartRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import com.vmzone.demo.dto.OrderBasicInfo;
import com.vmzone.demo.dto.ShoppingCartItemDTO;
import com.vmzone.demo.exceptions.BadRequestException;
import com.vmzone.demo.exceptions.NotEnoughQuantityException;
import com.vmzone.demo.exceptions.ResourceDoesntExistException;
import com.vmzone.demo.repository.OrderDetailsRepository;
import com.vmzone.demo.repository.OrderRepository;
import com.vmzone.demo.repository.ProductRepository;
import com.vmzone.demo.service.OrderService;
import com.vmzone.demo.service.UserService;

@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootTest
public class OrderServiceTests {
    private static final long DEFAULT_ID_TO_SEARCH = 1L;

    private static final User TEST_USER = new User( "User", "User", "user@abv.bg", "1234", Gender.Male, 0, null, null,
            null, null, 25, false);

    private static final Product TEST_PRODUCT = new Product(new Category("Shoes", null), "Product", "Information", 1, 24, 15, 1, "Details");


    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderDetailsRepository orderDetailsRepository;

    @Mock
    private UserService userService;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    public void testGetAllOrdersForUser() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(TEST_USER));
        orders.add(new Order(TEST_USER));
        orders.add(new Order(TEST_USER));

        when(orderRepository.findByUserAndIsDeletedIsFalse(TEST_USER)).thenReturn(orders);
        List<OrderBasicInfo> result = orderService.getAllOrdersForUser(TEST_USER);
        assertEquals(orders.size(), result.size());
    }

    @Test(expected = ResourceDoesntExistException.class)
    public void testGetOrderDetailsByIdWhenOrderDoesntExist() throws ResourceDoesntExistException {
        when(orderRepository.findByOrderIdAndUser(DEFAULT_ID_TO_SEARCH, TEST_USER)).thenReturn(null);
        orderService.getOrderDetailsById(DEFAULT_ID_TO_SEARCH, TEST_USER);
    }

    @Test
    public void testGetOrderDetailsByIdWhenOrderExist() throws ResourceDoesntExistException {
        List<OrderDetails> orderDetails = new ArrayList<>();
        orderDetails.add(new OrderDetails());
        orderDetails.add(new OrderDetails());
        orderDetails.add(new OrderDetails());

        Order order = new Order(TEST_USER);
        order.setOrderId(DEFAULT_ID_TO_SEARCH);
        when(orderRepository.findByOrderIdAndUser(DEFAULT_ID_TO_SEARCH, TEST_USER)).thenReturn(order);

        when(orderDetailsRepository.findByOrderId(DEFAULT_ID_TO_SEARCH)).thenReturn(orderDetails);
        List<OrderDetails> result = orderService.getOrderDetailsById(DEFAULT_ID_TO_SEARCH, TEST_USER);
        assertEquals(orderDetails.size(), result.size());
    }

    @Test(expected = ResourceDoesntExistException.class)
    public void testCreateNewOrderWhenShoppingCartIsEmpty() throws ResourceDoesntExistException, BadRequestException, NotEnoughQuantityException {
        when(shoppingCartRepository.findByUser(TEST_USER)).thenReturn(new ArrayList<>());
        orderService.createNewOrder(TEST_USER);
    }

    @Test(expected = BadRequestException.class)
    public void testCreateNewOrderWhenTransactionFails() throws ResourceDoesntExistException, BadRequestException, NotEnoughQuantityException {
        List<ShoppingCartItem> items = new ArrayList<>();
        items.add(new ShoppingCartItem(new Product(), new User(),  15));

        when(shoppingCartRepository.findByUser(TEST_USER)).thenReturn(items);
        orderService.createNewOrder(TEST_USER);
    }

    @Test
    public void testCreateNewOrderWithValidData() throws ResourceDoesntExistException, BadRequestException, NotEnoughQuantityException {
        List<ShoppingCartItem> items = new ArrayList<>();
        items.add(new ShoppingCartItem(new Product(), new User(), 15));
        when(productRepository.findById(items.get(0).getProduct().getProductId())).thenReturn(Optional.of(TEST_PRODUCT));
        when(shoppingCartRepository.findByUser(TEST_USER)).thenReturn(items);
        orderService.createNewOrder(TEST_USER);
    }

    @Test(expected = NotEnoughQuantityException.class)
    public void testCreateNewOrderWhenThereIsNotEnoughQuantityOfProduct() throws ResourceDoesntExistException, BadRequestException, NotEnoughQuantityException {
        List<ShoppingCartItem> items = new ArrayList<>();
        items.add(new ShoppingCartItem(new Product(), new User(),20));
        when(productRepository.findById(items.get(0).getProduct().getProductId())).thenReturn(Optional.of(TEST_PRODUCT));
        when(shoppingCartRepository.findByUser(TEST_USER)).thenReturn(items);
        orderService.createNewOrder(TEST_USER);
    }

    @Configuration
    static class Config {

    }
}
