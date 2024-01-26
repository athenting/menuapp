package com.widetech.menuapp.dao.entity;


import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "shopping_cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items;

    // This method adds an item to the shopping cart
    public void addToCart(CartItem item) {
        items.add(item);
        item.setCart(this);
    }

    // This method removes an item from the shopping cart
    public void removeFromCart(CartItem item) {
        items.remove(item);
        item.setCart(null);
    }
}
