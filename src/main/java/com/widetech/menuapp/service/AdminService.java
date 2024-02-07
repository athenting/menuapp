package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Admin;
import com.widetech.menuapp.dto.requests.AdminLoginDto;
import com.widetech.menuapp.dto.requests.AdminRegisterDto;
import com.widetech.menuapp.dto.responses.AdminLoginResultDto;

import java.util.List;
import java.util.Optional;

/**
 * Author: athen
 * Date: 2/1/2024
 * Description:
 */
public interface AdminService {

    AdminRegisterDto save(AdminRegisterDto admin);

    Admin updatePassword(Integer id, String newPassword);

    List<Admin> getAll();

    Admin findById(Integer id);

    Optional<Admin> findByEmail(String email);

    void deleteById(Integer id);

    AdminLoginResultDto login(AdminLoginDto dto);
}
