package com.widetech.menuapp.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "menu_item")
@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @NotNull
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    @NotNull
    private Menu menu;

    public MenuItem() {
    }

    public MenuItem(String name, String description, BigDecimal price, Menu menu) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.menu = menu;
    }
}
