package com.widetech.menuapp.dao.entity;

import cn.hutool.core.math.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "menu_item")
@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "price")
    @NotBlank
    private Money price;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    @NotNull
    private Menu menu;
}
