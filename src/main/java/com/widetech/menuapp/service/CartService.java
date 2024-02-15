package com.widetech.menuapp.service;

import com.widetech.menuapp.dto.requests.CartItemDto;
import com.widetech.menuapp.dto.responses.CartResultDto;
import com.widetech.menuapp.dto.responses.OrderResult;

import java.util.Optional;

public interface CartService {
    CartResultDto getCart(Integer customerId);

    CartResultDto addToCart(Integer customerId, CartItemDto item);

    void removeFromCart(Integer itemId);

    Optional<OrderResult> checkout(Integer cartId);
}