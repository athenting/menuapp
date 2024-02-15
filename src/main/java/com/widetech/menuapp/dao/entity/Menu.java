package com.widetech.menuapp.dao.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Table(name = "menu")
@Entity
@Getter
@Setter
@ToString
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuItem> menuItems = new ArrayList<>();


    public void addMenuItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
        menuItem.setMenu(this);
    }

    public void removeMenuItem(MenuItem menuItem) {
        this.menuItems.remove(menuItem);
        menuItem.setMenu(null);
    }

    public Menu() {
    }

    public Menu(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
