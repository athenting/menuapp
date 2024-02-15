package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.*;
import com.widetech.menuapp.dao.repository.*;
import com.widetech.menuapp.dto.requests.CartItemDto;
import com.widetech.menuapp.dto.responses.CartResultDto;
import com.widetech.menuapp.dto.responses.OrderResult;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.CartService;
import com.widetech.menuapp.utils.CartConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartItemRepository itemRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartConverter cartConverter;

    @Override
    public CartResultDto getCart(Integer customerId) {
        Cart cart = cartRepository.getCartByCustomerId(customerId);

        if (cart == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND);
        }

        return cartConverter.convertCartToDto(cart);
    }

    @Override
    @Transactional
    public CartResultDto addToCart(Integer customerId, CartItemDto itemDto) {
        Customer customer = getCustomer(customerId);
        MenuItem menuItem = getMenuItem(itemDto.getMenuItemId());
        CartItem cartItem = getCartItem(itemDto, menuItem);
        Cart cart = getCartIfNull(customerId, customer);
        cart.addToCart(cartItem);
        cart = cartRepository.save(cart);
        cartItem = itemRepository.save(cartItem);
        return cartConverter.createCartResultDto(customerId, cart, cartItem);
    }


    // Remove an item from the shopping cart
    @Override
    @Transactional
    public void removeFromCart(Integer itemId) {
        itemRepository.deleteById(itemId);
    }

    // Generate an order and empty the shopping cart
    @Override
    @Transactional
    public Optional<OrderResult> checkout(Integer cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isEmpty()) {
            throw new IllegalArgumentException("Invalid cartId: " + cartId);
        }

        Cart cart = optionalCart.get();
        //System.out.println("cart items get from database : " + cart.getItems().get(0));

        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setOrderDate(new Date());

        double totalPrice = cart.getItems().stream()
                .mapToDouble(cartItem -> cartItem.getPrice().intValue() * cartItem.getQuantity())
                .sum();
        order.setTotalPrice(new BigDecimal(totalPrice));

        order = orderRepository.save(order);

        OrderResult result = cartConverter.generateOrderResult(cart, order);

        //clear the items in customer's cart, cart items returns to the front end only for viewing
        cart.getItems().clear();
        //persist it in db
        cartRepository.save(cart);

        //return the generated Order object to proceed to payment
        return Optional.of(result);
    }

    private Customer getCustomer(Integer customerId) {
        return customerRepository.findById(customerId).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }

    private MenuItem getMenuItem(String menuItemId) {
        int id = Integer.parseInt(menuItemId);
        return menuItemRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.ENTITY_NOT_FOUND));
    }

    private CartItem getCartItem(CartItemDto itemDto, MenuItem menuItem) {
        CartItem cartItem = new CartItem();
        cartItem.setName(menuItem.getName());
        cartItem.setPrice(menuItem.getPrice());
        cartItem.setQuantity(Integer.valueOf(itemDto.getQuantity()));
        cartItem.setItem(menuItem);
        return cartItem;
    }

    private Cart getCartIfNull(Integer customerId, Customer customer) {
        Cart cart = cartRepository.getCartByCustomerId(customerId);
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            cart = cartRepository.save(cart);
        }
        return cart;
    }
}