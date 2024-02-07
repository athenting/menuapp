package com.widetech.menuapp.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "menu_admin")
@Getter
@Setter
@ToString
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "username")
    private String username;

    // password will be stored as encoded hash
    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;
}