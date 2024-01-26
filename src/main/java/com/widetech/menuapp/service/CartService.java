package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Cart;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
import com.widetech.menuapp.dao.entity.CartItem;
import com.widetech.menuapp.dao.entity.Order;

import java.util.Optional;

public interface CartService {
    Cart getCart(Integer customerId);

    Cart addToCart(Integer customerId, CartItem item);

    void removeFromCart(Integer itemId);

    Optional<Order> checkout(Integer cartId);
}