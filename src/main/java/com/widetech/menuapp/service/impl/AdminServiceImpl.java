package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.AdminService;

import com.widetech.menuapp.dao.entity.Admin;
import com.widetech.menuapp.dao.repository.AdminRepository;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Author: athen
 * Date: 2/1/2024
 * Description:
 */

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private final AdminRepository adminRepository;
    @Resource
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Admin save(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); // encode password
        return adminRepository.save(admin);
    }

    @Override
    public Admin updatePassword(Integer id, String newPassword) {
        Optional<Admin> admin = adminRepository.findById(id);

        if (admin.isPresent()) {
            Admin a = admin.get();
            a.setPassword(passwordEncoder.encode(newPassword)); // password encoding added here
            adminRepository.save(a);
            return a;
        } else {
            // throw an exception
            throw new BusinessException(ErrorCode.BUSINESS_ERROR_A2);
        }
    }

    @Override
    public List<Admin> getAll() {
        return adminRepository.findAll();
    }

    @Override
    public Admin findById(Integer id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        return Optional.ofNullable(adminRepository.findByEmail(email));
    }

    @Override
    public void deleteById(Integer id) {
        adminRepository.deleteById(id);
    }
}