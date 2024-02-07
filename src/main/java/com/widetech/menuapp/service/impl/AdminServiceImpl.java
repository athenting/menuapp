package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Admin;
import com.widetech.menuapp.dao.repository.AdminRepository;
import com.widetech.menuapp.dto.requests.AdminLoginDto;
import com.widetech.menuapp.dto.requests.AdminRegisterDto;
import com.widetech.menuapp.dto.responses.AdminLoginResultDto;
import com.widetech.menuapp.dto.responses.LoginStatus;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.AdminService;
import jakarta.annotation.Resource;
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

    public AdminRegisterDto save(AdminRegisterDto registerDto) {
        Admin admin = new Admin();
        admin.setPassword(passwordEncoder.encode(registerDto.getPassword())); // encode password
        admin.setEmail(registerDto.getEmail());
        admin.setStatus(LoginStatus.OFFLINE.name());
        adminRepository.save(admin);
        return registerDto;
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
        return Optional.ofNullable(adminRepository.findByEmail(email));
    }

    @Override
    public void deleteById(Integer id) {
        adminRepository.deleteById(id);
    }

    @Override
    public AdminLoginResultDto login(AdminLoginDto dto) {
        // 查询用户信息
        Admin admin = adminRepository.findByEmail(dto.getEmail());

        if (admin == null) {
            // 用户不存在
            throw new BusinessException(ErrorCode.ADMIN_NOT_EXIST);
        }

        // 判断密码是否正确
        if (!passwordEncoder.matches(dto.getPassword(), admin.getPassword())) {
            // 密码错误
            throw new BusinessException(ErrorCode.USER_PASSWORD_ERROR);
        }

        if (!StringUtils.equals(LoginStatus.OFFLINE.name(), admin.getStatus())) {
            //  status error
            throw new BusinessException(ErrorCode.ADMIN_STATUS_ERROR);
        }
        // login success
        admin.setStatus(LoginStatus.ONLINE.name());
        adminRepository.save(admin);
        return AdminLoginResultDto.builder().id(admin.getId()).email(admin.getEmail()).status(admin.getStatus()).build();

    }
}