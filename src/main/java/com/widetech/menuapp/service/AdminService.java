package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Admin;

import java.util.List;
import java.util.Optional;

/**
 * Author: athen
 * Date: 2/1/2024
 * Description:
 */
public interface AdminService {

    Admin save(Admin admin);

    Admin updatePassword(Integer id, String newPassword);

    List<Admin> getAll();

    Admin findById(Integer id);

    Optional<Admin> findByEmail(String email);

    void deleteById(Integer id);
}
