package com.widetech.menuapp;

import com.widetech.menuapp.dao.repository.CustomerRepository;
import com.widetech.menuapp.dto.requests.CustomerDto;
import com.widetech.menuapp.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: athen
 * Date: 2/7/2024
 * Description:
 */
@SpringBootTest
public class CustomerServiceIntegratedTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setup() {
        // clean up database before each test to ensure data consistency
        customerRepository.deleteAll();
    }

    @Test
    public void createCustomerSuccessfully() {
        CustomerDto customer = new CustomerDto("", "1234567890");
        CustomerDto savedCustomer = customerService.createCustomer(customer);
        assertEquals("1234567890", savedCustomer.getName());
        assertEquals("1234567890", savedCustomer.getPhoneNumber());
    }

    @Test
    public void updateCustomerSuccessfully() {
        // Creates an initial Customer
        CustomerDto customer = new CustomerDto("OldName", "1234567890");
        CustomerDto savedCustomer = customerService.createCustomer(customer);

        // Updates the Customer's name
        CustomerDto updatedCustomer = new CustomerDto("NewName", "1234567890");
        savedCustomer = customerService.updateCustomer("1234567890", updatedCustomer);

        assertEquals("NewName", savedCustomer.getName());
        assertEquals("1234567890", savedCustomer.getPhoneNumber());
    }

    @Test
    public void deleteCustomerSuccessfully() {
        // Creates an initial Customer
        CustomerDto customer = new CustomerDto("Name", "1234567890");
        CustomerDto savedCustomer = customerService.createCustomer(customer);

        customerService.deleteCustomer("1234567890");

        assertFalse(customerService.getCustomerByPhoneNumber("1234567890").isPresent());
    }

    @Test
    public void getCustomerByPhoneNumberSuccessfully() {
        // Creates an initial Customer
        CustomerDto customer = new CustomerDto("Name", "1234567890");
        CustomerDto savedCustomer = customerService.createCustomer(customer);

        Optional<CustomerDto> fetchedCustomer = customerService.getCustomerByPhoneNumber("1234567890");

        assertTrue(fetchedCustomer.isPresent());
        assertEquals("Name", fetchedCustomer.get().getName());
        assertEquals("1234567890", fetchedCustomer.get().getPhoneNumber());
    }
}