package com.widetech.menuapp;

import com.widetech.menuapp.dao.entity.*;
import com.widetech.menuapp.dao.repository.MenuRepository;
import com.widetech.menuapp.dto.requests.CartItemDto;
import com.widetech.menuapp.dto.responses.CartResultDto;
import com.widetech.menuapp.dto.responses.OrderResult;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.CartService;
import com.widetech.menuapp.service.MenuService;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: athen
 * Date: 2/7/2024
 * Description:
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class CartServiceIntegratedTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CartService cartService;

    @Resource
    private MenuRepository menuRepository;

    @Resource
    private MenuService menuService;

    @Test
    @Rollback(value = false)
    public void addToCartTest() {
        // Arrange
        Customer customer = new Customer("defaultName1", "18888888888");
        entityManager.persist(customer);

        Optional<Menu> menu = menuRepository.findById(52);
        MenuItem menuItem = menuService.getItemById("152");

        entityManager.flush();

        CartItemDto itemDto = new CartItemDto(); // add necessary values/args to the constructor
        itemDto.setMenuItemId("152"); // set the menuItemId
        itemDto.setQuantity("2");

        // Act
        CartResultDto cartResultDto = cartService.addToCart(customer.getId(), itemDto);

        // Assert
        assertNotNull(cartResultDto);
        assertEquals(customer.getId().toString(), cartResultDto.getCustomerId());
        // add more assertions here as needed
    }


    @Test
    public void removeFromCartTest() {
        // Arrange
        CartItem cartItem = new CartItem(); // fill with necessary values
        entityManager.persist(cartItem);

        // Act & Assert
        assertDoesNotThrow(() -> cartService.removeFromCart(cartItem.getId()));
        assertNull(entityManager.find(CartItem.class, cartItem.getId()));
    }

    @Test
    @Rollback(value = false)
    public void checkoutTest() {
        // Arrange
//        Customer customer = new Customer("defaultName1", "18888888888");
//        entityManager.persist(customer);
//
//        Cart cart = new Cart();
//        cart.setCustomer(customer);
//        entityManager.persist(cart);
//
//        CartItem cartItem = new CartItem();
//        // Set mandatory properties for CartItem
//        cartItem.setName("ItemName");
//        cartItem.setQuantity(1);
//        // Please set other properties if there are
//
//        // Adding CartItem to Cart before persisting
//        cart.getItems().add(cartItem);
//        cartItem.setCart(cart);
//
//        entityManager.persist(cartItem);
//        entityManager.flush();

        // Act
        Optional<OrderResult> orderResultOpt = cartService.checkout(452);
        System.out.println(orderResultOpt);

        // Assert
        assertTrue(orderResultOpt.isPresent());
        OrderResult orderResult = orderResultOpt.get();
        // Replace assertions below with your expected outputs
        assertNotNull(orderResult);
//        assertEquals(cart.getCustomer().getId().toString(), orderResult.getCustomerId());
    }

    @Test
    @Rollback(false) // this will actually change the dataset in db
    public void getCartTestSuccessful() {
        // arrange
        Customer customer = new Customer();   // Assume Customer class exists
        customer.setName("Test Customer");   // Replace with actual method to set name
        entityManager.persist(customer);
        entityManager.flush();

        Cart cart = new Cart();               // Assume Cart class exists
        cart.setCustomer(customer);

        // inject test data to Database
        entityManager.persist(cart);
        entityManager.flush();

        // act
        CartResultDto savedCart = cartService.getCart(customer.getId());

        // assert
        assertNotNull(savedCart.getCartId());
        assertEquals(cart.getCustomer().getId().toString(), savedCart.getCustomerId());
    }

    @Test
    public void getCartTestFailure() {
        // act and assert, we expect an error because no customer with this id exists
        assertThrows(BusinessException.class, () -> {
            cartService.getCart(99);
        });
    }


}
