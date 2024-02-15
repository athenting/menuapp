package com.widetech.menuapp.controller;

import com.widetech.menuapp.dto.requests.CartItemDto;
import com.widetech.menuapp.dto.responses.CartResultDto;
import com.widetech.menuapp.dto.responses.OrderResult;
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
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // Get shopping cart for a specific customer
    @GetMapping("/{customerId}")
    public CartResultDto getCart(@PathVariable Integer customerId) {
        return cartService.getCart(customerId);
    }

    // Add a product to a specific customer's shopping cart
    @PostMapping("/{customerId}/add")
    public CartResultDto addToCart(@PathVariable Integer customerId, @RequestBody CartItemDto item) {
        return cartService.addToCart(customerId, item);
    }

    // Remove a product from the shopping cart
    @DeleteMapping("/remove/{itemId}")
    public void removeFromCart(@PathVariable Integer itemId) {
        cartService.removeFromCart(itemId);
    }

    // Generate an order and empty the shopping cart
    @PostMapping("/{cartId}/checkout")
    public Optional<OrderResult> checkout(@PathVariable Integer cartId) {
        return cartService.checkout(cartId);
    }
}