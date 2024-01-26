package com.widetech.menuapp.controller;

import com.widetech.menuapp.dao.entity.Cart;
import com.widetech.menuapp.dao.entity.CartItem;
import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Get shopping cart for a specific customer
    @GetMapping("/{customerId}")
    public Cart getCart(@PathVariable Integer customerId) {
        return cartService.getCart(customerId);
    }

    // Add a product to a specific customer's shopping cart
    @PostMapping("/{customerId}/add")
    public Cart addToCart(@PathVariable Integer customerId, @RequestBody CartItem item) {
        return cartService.addToCart(customerId, item);
    }

    // Remove a product from the shopping cart
    @DeleteMapping("/remove/{itemId}")
    public void removeFromCart(@PathVariable Integer itemId) {
        cartService.removeFromCart(itemId);
    }

    // Generate an order and empty the shopping cart
    @PostMapping("/{cartId}/checkout")
    public Optional<Order> checkout(@PathVariable Integer cartId) {
        return cartService.checkout(cartId);
    }
}