package com.widetech.menuapp.utils;

import com.widetech.menuapp.dao.entity.Cart;
import com.widetech.menuapp.dao.entity.CartItem;
import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dto.responses.CartItemResultDto;
import com.widetech.menuapp.dto.responses.CartResultDto;
import com.widetech.menuapp.dto.responses.OrderResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: athen
 * Date: 2/15/2024
 * Description:
 */
@Component
public class CartConverter {
    public CartResultDto convertCartToDto(Cart cart) {
        CartResultDto resultDto = new CartResultDto();
        resultDto.setCartId(String.valueOf(cart.getId()));
        resultDto.setCustomerId(String.valueOf(cart.getCustomer().getId()));
        List<CartItemResultDto> itemResults = cart.getItems()
                .stream()
                .map(this::createCartItemResultDtoWithoutCart)
                .collect(Collectors.toList());
        resultDto.setCartItems(itemResults);
        return resultDto;
    }

    private CartItemResultDto createCartItemResultDtoWithoutCart(CartItem item) {
        CartItemResultDto itemResult = new CartItemResultDto();
        itemResult.setCartItemId(String.valueOf(item.getId()));
        itemResult.setName(item.getName());
        itemResult.setPrice(String.valueOf(item.getPrice()));
        itemResult.setMenuItemId(String.valueOf(item.getItem().getId()));
        return itemResult;
    }

    public CartResultDto createCartResultDto(Integer customerId, Cart cart, CartItem cartItem) {
        CartResultDto result = new CartResultDto();
        result.setCustomerId(customerId.toString());
        result.setCartId(cart.getId().toString());
        CartItemResultDto cartItemResultDto = createCartItemResultDto(cartItem, String.valueOf(cart.getId()));
        result.getCartItems().add(cartItemResultDto);
        return result;
    }

    public CartItemResultDto createCartItemResultDto(CartItem cartItem, String cartId) {
        CartItemResultDto cartItemResultDto = new CartItemResultDto();
        cartItemResultDto.setMenuItemId(cartItem.getItem().getId().toString());
        cartItemResultDto.setQuantity(cartItem.getQuantity().toString());
        cartItemResultDto.setName(cartItem.getName());
        cartItemResultDto.setPrice(cartItem.getPrice().toString());
        cartItemResultDto.setCartItemId(cartItem.getId().toString());
        cartItemResultDto.setCart(cartId);
        return cartItemResultDto;
    }

    public OrderResult generateOrderResult(Cart cart, Order order) {
        List<CartItemResultDto> itemResultDtos = new ArrayList<>();
        for (CartItem cartItem : cart.getItems()) {
            itemResultDtos.add(createCartItemResultDto(cartItem, String.valueOf(cart.getId())));
        }
        return OrderResult.builder()
                .totalPrice(String.valueOf(order.getTotalPrice()))
                .customerId(String.valueOf(order.getCustomer().getId()))
                .id(String.valueOf(order.getId()))
                .orderDate(order.getOrderDate().toString())
                .cartItems(itemResultDtos)
                .build();
    }
}