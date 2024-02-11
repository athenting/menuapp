package com.widetech.menuapp;

import com.widetech.menuapp.dao.entity.Admin;
import com.widetech.menuapp.dao.repository.AdminRepository;
import com.widetech.menuapp.dto.requests.AdminLoginDto;
import com.widetech.menuapp.dto.requests.AdminRegisterDto;
import com.widetech.menuapp.dto.responses.AdminLoginResultDto;
import com.widetech.menuapp.dto.responses.AdminRegisterResultDto;
import com.widetech.menuapp.dto.responses.LoginStatus;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.AdminService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: athen
 * Date: 2/8/2024
 * Description:
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AdminServiceIntegratedTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Test
    @Rollback(value = false)
    public void testCreateAdmin() {
        AdminRegisterDto adminRegisterDto = new AdminRegisterDto();
        adminRegisterDto.setEmail("testEmail@test.com");
        adminRegisterDto.setPassword("testPassword");
        adminRegisterDto.setName("testName1");

        AdminRegisterResultDto savedAdmin = adminService.save(adminRegisterDto);

        assertNotNull(savedAdmin);
        assertEquals(adminRegisterDto.getEmail(), savedAdmin.getEmail());
    }

    @Test
    public void testUpdateAdminPassword() {
        // Arrange
        AdminRegisterDto adminRegisterDto = new AdminRegisterDto();
        adminRegisterDto.setEmail("testEmail@test.com");
        adminRegisterDto.setPassword("testPassword");

        AdminRegisterResultDto savedAdmin = adminService.save(adminRegisterDto);
        assertNotNull(savedAdmin);
        assertEquals(adminRegisterDto.getEmail(), savedAdmin.getEmail());

        // Act - update password
        Admin updatedAdmin = adminService.updatePassword(savedAdmin.getId(), "newPassword");

        // Assert
        assertNotNull(updatedAdmin);
        assertTrue(new BCryptPasswordEncoder().matches("newPassword", updatedAdmin.getPassword()));
    }

    @Test
    public void testFindById() {
        // Arrange
        AdminRegisterDto adminRegisterDto = new AdminRegisterDto();
        adminRegisterDto.setEmail("testEmail1@test.com");
        adminRegisterDto.setPassword("testPassword");

        AdminRegisterResultDto savedAdmin = adminService.save(adminRegisterDto);
        assertNotNull(savedAdmin);
        assertEquals(adminRegisterDto.getEmail(), savedAdmin.getEmail());

        // Act - find by id
        Admin foundAdmin = adminService.findById(savedAdmin.getId());

        // Assert
        assertNotNull(foundAdmin);
        assertEquals(adminRegisterDto.getEmail(), foundAdmin.getEmail());
    }

    @Test
    public void testDeleteAdmin() {
        // Arrange
        AdminRegisterDto adminRegisterDto = new AdminRegisterDto();
        adminRegisterDto.setEmail("testEmail3@test.com");
        adminRegisterDto.setPassword("testPassword3");

        AdminRegisterResultDto savedAdmin = adminService.save(adminRegisterDto);
        assertNotNull(savedAdmin);
        assertEquals(adminRegisterDto.getEmail(), savedAdmin.getEmail());

        // Act - delete admin
        adminService.deleteById(savedAdmin.getId());

        // Now clear the persistence context to force a database hit
        entityManager.clear();

        // Attempt to retrieve the deleted admin
        Admin deletedAdmin = entityManager.find(Admin.class, savedAdmin.getId());

        // Assert
        assertNull(deletedAdmin);
    }

    @Test
    //@Rollback(value = false)
    public void testAdminLoginSuccess() {
        AdminRegisterDto adminRegisterDto = new AdminRegisterDto();
        adminRegisterDto.setEmail("testLoginEmail4@test.com");
        adminRegisterDto.setPassword("testLoginPassword");

        AdminRegisterResultDto savedAdmin = adminService.save(adminRegisterDto);
        assertNotNull(savedAdmin);
        assertEquals(adminRegisterDto.getEmail(), savedAdmin.getEmail());

        AdminLoginDto loginDto = new AdminLoginDto();
        loginDto.setEmail("testLoginEmail4@test.com");
        loginDto.setPassword("testLoginPassword");

        AdminLoginResultDto loginResult = adminService.login(loginDto);

        assertNotNull(loginResult);
        assertEquals("ONLINE", loginResult.getStatus());
    }

    @Test
    public void testAdminLoginFail() {
        AdminRegisterDto adminRegisterDto = new AdminRegisterDto();
        adminRegisterDto.setEmail("testLoginEmail5@test.com");
        adminRegisterDto.setPassword("testLoginPassword");

        AdminRegisterResultDto savedAdmin = adminService.save(adminRegisterDto);
        assertNotNull(savedAdmin);
        assertEquals(adminRegisterDto.getEmail(), savedAdmin.getEmail());

        AdminLoginDto loginDto = new AdminLoginDto();
        loginDto.setEmail("testLoginEmail5@test.com");
        loginDto.setPassword("wrongPassword");

        assertThrows(BusinessException.class, () -> adminService.login(loginDto));
    }


    @Test
    public void logoutSuccessfully() {
        // Setup initial data
        Admin admin = new Admin();
        admin.setEmail("testemail6@mail.com");
        admin.setPassword("password6");
        admin.setStatus(LoginStatus.ONLINE.name());
        adminRepository.save(admin);

        // Execute the method
        adminService.logout("testemail6@mail.com");

        // Retrieve the potentially updated admin
        Admin possiblyUpdatedAdmin = adminRepository.findByEmail("testemail6@mail.com").getFirst();

        // Check that the status has been updated
        assertEquals(LoginStatus.OFFLINE.name(), possiblyUpdatedAdmin.getStatus());
    }

}