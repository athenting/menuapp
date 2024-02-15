package com.widetech.menuapp.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
@Entity
@Table(name = "cart_item")
@Getter
@Setter
@ToString
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private MenuItem item;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "price")
    private BigDecimal price;

}