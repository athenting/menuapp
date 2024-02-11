package com.widetech.menuapp.controller;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Admin;
import com.widetech.menuapp.dto.requests.AdminLoginDto;
import com.widetech.menuapp.dto.requests.AdminRegisterDto;
import com.widetech.menuapp.dto.responses.AdminLoginResultDto;
import com.widetech.menuapp.dto.responses.AdminRegisterResultDto;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The AdminAuthenticationController class handles the authentication and management of admin users in the system.
 *
 * @Author: Dian Ding
 * @Date: 24/01/2024
 */
@RestController
@RequestMapping("/api/admin")
public class AdminManageController {

    @Autowired
    AdminService adminService;

    /**
     * get all existing admin users
     */
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAll();
    }

    /**
     * Retrieves an admin user by their ID.
     *
     * @param id the ID of the admin user
     * @return the admin user with the given ID
     */
    @GetMapping("/{id}")
    public Admin getAdmin(@PathVariable Integer id) {
        return adminService.findById(id);
    }

    /**
     * Creates a new admin user.
     *
     * @param registerDto the admin user to be created
     * @return the created admin user
     */
    @PostMapping("/create")
    public RestResponse<AdminRegisterResultDto> createAdmin(@RequestBody AdminRegisterDto registerDto) {
        return RestResponse.success(adminService.save(registerDto));
    }

    /**
     * Updates the password of an admin user.
     * once created, admin name cannot be modified
     * user can only change the password
     *
     * @param id       the ID of the admin user
     * @param password the new password
     * @return the updated admin user
     */
    @PutMapping("/{id}")
    public Admin updateAdminPassword(@PathVariable Integer id, @RequestParam String password) {
        return adminService.updatePassword(id, password);
    }

    /**
     * Deletes an admin user by their ID. This method is used to remove an admin user from the system.
     *
     * @param id the ID of the admin user to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Integer id) {
        adminService.deleteById(id);
    }

    @PostMapping("/login")
    public RestResponse<AdminLoginResultDto> loginAdmin(@RequestBody AdminLoginDto loginDto) {
        try {
            AdminLoginResultDto resultDto = adminService.login(loginDto);
            return RestResponse.success(resultDto);
        } catch (Exception ex) {
            return RestResponse.fail(ErrorCode.SYSTEM_ERROR);
        }
    }

    @PostMapping("/logout/{id}")
    public RestResponse<Void> logout(@PathVariable Integer id, @RequestParam String email) {
        try {
            adminService.logout(email);
            return RestResponse.success();
        } catch (Exception ex) {
            return RestResponse.fail(ErrorCode.SYSTEM_ERROR);
        }
    }

}