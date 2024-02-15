package com.widetech.menuapp.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * record how many items the customer ordered in total
 */
@Entity
@Table(name = "customer_order_detail")
@Getter
@Setter
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "price")
    private BigDecimal price;

    /**
     * how many dishes in total
     */
    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName="id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id",referencedColumnName="id")
    private MenuItem item;
}