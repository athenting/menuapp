package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.dao.entity.Cart;
import com.widetech.menuapp.dao.entity.CartItem;
import com.widetech.menuapp.dao.entity.Customer;
import com.widetech.menuapp.dao.entity.Order;
import com.widetech.menuapp.dao.repository.CartItemRepository;
import com.widetech.menuapp.dao.repository.CartRepository;
import com.widetech.menuapp.dao.repository.CustomerRepository;
import com.widetech.menuapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Get the shopping cart for a specific customer
    public Cart getCart(Integer customerId) {
        return cartRepository.getCartByCustomerId(customerId);
    }

    // Add an item to a specific customer's shopping cart
    public Cart addToCart(Integer customerId, CartItem item) {

        //check if the customer has already own a cart, if not, create a new cart for him/her
        Cart cart = Optional.ofNullable(getCart(customerId))
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    Customer customer = customerRepository.getReferenceById(customerId);
                    newCart.setCustomer(customer);
                    return newCart;
                });

        cart.addToCart(item);
        return cartRepository.save(cart);
    }

    // Remove an item from the shopping cart
    public void removeFromCart(Integer itemId) {
        itemRepository.deleteById(itemId);
    }

    // Generate an order and empty the shopping cart
    public Optional<Order> checkout(Integer cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isPresent()) {
            Order order = new Order();
            //clear the items in customer's cart
            cart.get().getItems().clear();
            //persist it in db
            cartRepository.save(cart.get());

            //return the generated Order object to proceed to payment
            return Optional.of(order);
        } else {
            return Optional.empty();
        }
    }
}