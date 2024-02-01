package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.dao.entity.Customer;
import com.widetech.menuapp.dao.repository.CustomerRepository;
import com.widetech.menuapp.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Author: athen
 * Date: 1/26/2024
 * Description:
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        //if name empty,set phone number as name
        if (customer.getName() == null || customer.getName().isEmpty()) {
            customer.setName(customer.getPhoneNumber());
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(String phoneNumber, Customer customer) {
        Customer existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);
        BeanUtils.copyProperties(customer, existingCustomer, "phoneNumber");
        return customerRepository.saveAndFlush(existingCustomer);
    }

    @Override
    public void deleteCustomer(String phoneNumber) {
        customerRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<Customer> getCustomerByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(customerRepository.findByPhoneNumber(phoneNumber));
    }

}