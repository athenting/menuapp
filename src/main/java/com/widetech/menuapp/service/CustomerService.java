package com.widetech.menuapp.service;

import com.widetech.menuapp.dto.requests.CustomerDto;

import java.util.Optional;

/**
 * Author: athen
 * Date: 1/26/2024
 * Description:
 */
public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customer);

    CustomerDto updateCustomer(String phoneNumber, CustomerDto customerDto);

    void deleteCustomer(String phoneNumber);

    Optional<CustomerDto> getCustomerByPhoneNumber(String phoneNumber);

}