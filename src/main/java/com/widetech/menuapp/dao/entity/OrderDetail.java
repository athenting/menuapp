package com.widetech.menuapp.dao.entity;

import cn.hutool.core.math.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * record how many items the customer ordered in total
 */
@Entity
@Table(name = "order_detail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "price")
    private Money price;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private MenuItem item;
}