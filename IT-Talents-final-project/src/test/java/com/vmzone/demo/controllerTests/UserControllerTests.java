package com.vmzone.demo.controllerTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmzone.demo.dto.*;
import com.vmzone.demo.enums.Gender;
import com.vmzone.demo.models.User;
import com.vmzone.demo.service.UserService;
import com.vmzone.demo.utils.SessionManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@PrepareForTest(SessionManager.class)

public class UserControllerTests {

    private static final String USER_REGISTER_URL = "/user/register";
    private static final String USER_LOGIN_URL = "/user/login";
    private static final String INVALID_LONG_PASSWORD = "asdasasdasasdasasdasasdasasdasasdasasdasasdasasdasasdas";
    private CartProductDTO cartProductDTO;
    private RegisterDTO registerDTO;
    private LoginDTO loginDTO;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    private MockHttpSession mockHttpSession;

    private User user;

    @Before
    public void init() {
        this.registerDTO = new RegisterDTO("stefan", "rangelov", "a@abv.bg",
                "12345", "Male", 1);
        this.loginDTO = new LoginDTO("a@abv.bg", "12345");
        this.cartProductDTO = new CartProductDTO(1L, 19);

        user = new User(1L, registerDTO.getFirstName(), registerDTO.getLastName(), registerDTO.getEmail(), registerDTO.getPassword(),
                Gender.valueOf(registerDTO.getGender()), 1, 1, null, null, null, null, 20, false);

        mockHttpSession = new MockHttpSession();
        mockHttpSession.setAttribute("user", user);
    }

    @Test
    public void registeringUserShouldReturnUserObject() throws Exception {
        User expectedUser = new User(registerDTO.getFirstName(), registerDTO.getLastName(), registerDTO.getEmail(), registerDTO.getPassword(),
                Gender.valueOf(registerDTO.getGender()), registerDTO.getIsSubscribed(), null, null, null,
                null, 16, false);

        Mockito.when(service.register(Mockito.any(RegisterDTO.class))).thenReturn(expectedUser);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                .content(objectMapper.writeValueAsString(registerDTO))
                .contentType("application/json"))
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(expectedUser);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(expectedResponseBody, actualResponseBody);
    }

    private void performRequestForUrlAndContent(String url, Object dto, ResultMatcher result) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .session(mockHttpSession)
                .content(objectMapper.writeValueAsString(dto))
                .contentType("application/json"))
                .andExpect(result);
    }

    @Test
    public void registeringUserWithBlankUsernameShouldReturnBadRequest() throws Exception {
        registerDTO.setFirstName(null);
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isBadRequest());
    }

    @Test
    public void registeringUserWithBlankLastNameShouldReturnBadRequest() throws Exception {
        registerDTO.setLastName(null);
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isBadRequest());
    }

    @Test
    public void registeringUserWithInvalidEmailPatternShouldReturnBadRequest() throws Exception {
        registerDTO.setEmail("asd");
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isBadRequest());
    }

    @Test
    public void registeringUserWithLessThanMinPasswordLenghtShouldReturnBadRequest() throws Exception {
        registerDTO.setPassword("asd");
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isBadRequest());
    }

    @Test
    public void registeringUserWihtMoreThanMaxPasswordLenghtShouldReturnBadRequest() throws Exception {
        registerDTO.setPassword(INVALID_LONG_PASSWORD);
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isBadRequest());
    }

    @Test
    public void registeringUserWithInvalidGenderShouldReturnBadRequest() throws Exception {
        registerDTO.setGender("Other");
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isBadRequest());
    }

    @Test
    public void registeringWithInvalidSubscribedValueShouldReturnBadRequest() throws Exception {
        registerDTO.setIsSubscribed(23);
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isBadRequest());
    }

    @Test
    public void registeringWithCorrectDataShouldReturnStatusOk() throws Exception {
        performRequestForUrlAndContent(USER_REGISTER_URL, registerDTO, status().isOk());
    }

    @Test
    public void loginWithInvalidEmailPatternShouldReturnBadRequest() throws Exception {
        loginDTO.setEmail("asd@");
        performRequestForUrlAndContent(USER_LOGIN_URL, loginDTO, status().isBadRequest());
    }

    @Test
    public void loginWithLessThanMinPasswordLengthShouldReturnBadRequest() throws Exception {
        loginDTO.setPassword("12");
        performRequestForUrlAndContent(USER_LOGIN_URL, loginDTO, status().isBadRequest());
    }

    @Test
    public void loginWithMoreThanMaxPasswordLenghtShouldReturnBadRequest() throws Exception {
        loginDTO.setPassword(INVALID_LONG_PASSWORD);
        performRequestForUrlAndContent(USER_LOGIN_URL, loginDTO, status().isBadRequest());
    }

    @Test
    public void successfulLogin() throws Exception {
        Mockito.when(this.service.login(Mockito.any(LoginDTO.class))).thenReturn(new User());
        performRequestForUrlAndContent(USER_LOGIN_URL, loginDTO, status().isOk());
    }

    @Test
    public void getShoppingCartWithNoLoggedUserShouldThrowError() throws Exception {
        mockHttpSession.setAttribute("user", null);

        mockMvc.perform(MockMvcRequestBuilders.get("/cart").session(mockHttpSession)
                .contentType("application/json"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getUserShoppingCartShouldReturnCorrectObject() throws Exception {

        List<ShoppingCartItemDTO> expectedObject = Arrays.asList(new ShoppingCartItemDTO(1L, "Item", 10, 10));
        Mockito.when(service.getShoppingCart(Mockito.any(User.class)))
                .thenReturn(expectedObject);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/cart").session(mockHttpSession)
                .contentType("application/json"))
                .andReturn();

        String expectedResponseBody = objectMapper.writeValueAsString(expectedObject);
        String actualResponseBody = mvcResult.getResponse().getContentAsString();

        Assert.assertEquals(expectedResponseBody, actualResponseBody);
    }

    @Test
    public void addProductToCartWithNoLoggedUserShouldThrowError() throws Exception {
        mockHttpSession.setAttribute("user", null);

        performRequestForUrlAndContent("/product/cart", cartProductDTO, status().isUnauthorized());
    }

    @Test
    public void addProductToCartWithNoIdPresentShouldReturnBadRequest() throws Exception {
        cartProductDTO.setProductId(null);
        performRequestForUrlAndContent("/product/cart", cartProductDTO, status().isBadRequest());
    }

    @Test
    public void addProductToCartWithInvalidQuantityShouldReturnBadRequest() throws Exception {
        cartProductDTO.setQuantity(0);
        performRequestForUrlAndContent("/product/cart", cartProductDTO, status().isBadRequest());
    }

    @Test
    public void addProductToCartWithCorrectInput() throws Exception {
        long id = 1L;
        Mockito.when(service.addProductToCart(Mockito.any(CartProductDTO.class), Mockito.any(User.class))).thenReturn(id);


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/product/cart").session(mockHttpSession)
                .content(objectMapper.writeValueAsString(cartProductDTO))
                .contentType("application/json"))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        Assert.assertEquals(id, Long.parseLong(result));
    }
}