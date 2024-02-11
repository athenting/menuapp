package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Admin;
import com.widetech.menuapp.dao.repository.AdminRepository;
import com.widetech.menuapp.dto.requests.AdminLoginDto;
import com.widetech.menuapp.dto.requests.AdminRegisterDto;
import com.widetech.menuapp.dto.responses.AdminLoginResultDto;
import com.widetech.menuapp.dto.responses.AdminRegisterResultDto;
import com.widetech.menuapp.dto.responses.LoginStatus;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.AdminService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public AdminRegisterResultDto save(AdminRegisterDto registerDto) {

        if (!adminRepository.findByEmail(registerDto.getEmail()).isEmpty()) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_REGISTERED);
        }

        Admin admin = new Admin();
        admin.setPassword(passwordEncoder.encode(registerDto.getPassword())); // encode password
        admin.setEmail(registerDto.getEmail());
        admin.setStatus(LoginStatus.OFFLINE.name());
        admin.setUsername(registerDto.getName());
        Admin savedAdmin = adminRepository.save(admin);

        return AdminRegisterResultDto.builder().id(savedAdmin.getId()).name(savedAdmin.getUsername()).email(savedAdmin.getEmail()).status(savedAdmin.getStatus()).build();
    }

    @Override
    @Transactional
    public Admin updatePassword(Integer id, String newPassword) {
        Optional<Admin> admin = adminRepository.findById(id);

        if (admin.isPresent()) {
            Admin a = admin.get();
            a.setPassword(passwordEncoder.encode(newPassword)); // password encoding added here
            adminRepository.save(a);
            return a;
        } else {
            // throw an exception
            throw new BusinessException(ErrorCode.PASSWORD_ENCODE_ERROR);
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
        List<Admin> admins = adminRepository.findByEmail(email);
        Admin firstAdminWithEmail = admins != null ? admins.stream().findFirst().orElse(null) : null;
        return Optional.ofNullable(firstAdminWithEmail);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        adminRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AdminLoginResultDto login(AdminLoginDto dto) {
        // Retrieve all admins associated with the email
        List<Admin> admins = adminRepository.findByEmail(dto.getEmail());

        if (admins.isEmpty()) {
            // No admin with the provided email exists
            throw new BusinessException(ErrorCode.ADMIN_NOT_EXIST);
        }

        // Select first admin from the list
        Admin admin = admins.getFirst();

        // Check if the passwords match
        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            // Password does not match
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }

        if (!StringUtils.equals(LoginStatus.OFFLINE.name(), admin.getStatus())) {
            //  Admin is not offline
            throw new BusinessException(ErrorCode.ADMIN_STATUS_ERROR);
        }
        // Set the admin status to online
        admin.setStatus(LoginStatus.ONLINE.name());
        adminRepository.save(admin);
        return AdminLoginResultDto.builder()
                .id(admin.getId())
                .name(admin.getUsername())
                .email(admin.getEmail())
                .status(admin.getStatus())
                .build();
    }

    @Override
    @Transactional
    public void  logout(String email) {
        // Retrieve all admins associated with the email
        List<Admin> admins = adminRepository.findByEmail(email);

        if (admins.isEmpty()) {
            // No admin with the provided email exists
            throw new BusinessException(ErrorCode.ADMIN_NOT_EXIST);
        }

        // Select first admin from the list
        Admin admin = admins.getFirst();

        // Check if the admin is already offline
        if (StringUtils.equals(LoginStatus.OFFLINE.name(), admin.getStatus())) {
            // Admin already offline
            throw new BusinessException(ErrorCode.ADMIN_STATUS_ERROR);
        }

        // Set the admin status to offline
        admin.setStatus(LoginStatus.OFFLINE.name());
        adminRepository.save(admin);
    }
}