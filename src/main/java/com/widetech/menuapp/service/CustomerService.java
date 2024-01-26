package com.widetech.menuapp.service;

import com.widetech.menuapp.dao.entity.Customer;

import java.util.Optional;

/**
 * Author: athen
 * Date: 1/26/2024
 * Description:
 */
public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer updateCustomer(String phoneNumber, Customer customer);

    void deleteCustomer(String phoneNumber);

    Optional<Customer> getCustomerByPhoneNumber(String phoneNumber);

}