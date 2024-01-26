package com.widetech.menuapp.dao.entity;

import cn.hutool.core.math.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: athen
 * Date: 1/25/2024
 * Description:
 */
@Entity
@Table(name = "cart_item")
@Getter
@Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private MenuItem item;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "price")
    private Money price;

}