package com.widetech.menuapp.service.impl;

import com.widetech.menuapp.constants.ErrorCode;
import com.widetech.menuapp.dao.entity.Customer;
import com.widetech.menuapp.dao.repository.CustomerRepository;
import com.widetech.menuapp.dto.requests.CustomerDto;
import com.widetech.menuapp.exception.BusinessException;
import com.widetech.menuapp.service.CustomerService;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
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
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {

        if (null != customerRepository.findByPhoneNumber(customerDto.getPhoneNumber())) {
            throw new BusinessException(ErrorCode.PHONE_ALREADY_REGISTERED);
        }

        Customer customer = new Customer();

        //if name empty,set phone number as name
        if (customerDto.getName() == null || customerDto.getName().isEmpty()) {
            customer.setName(customerDto.getPhoneNumber());
        } else {
            customer.setName(customerDto.getName());
        }

        customer.setPhoneNumber(customerDto.getPhoneNumber());

        Customer createdCustomer = customerRepository.save(customer);

        return CustomerDto.builder().
                name(createdCustomer.getName()).
                phoneNumber(createdCustomer.getPhoneNumber()).
                build();
    }

    /**
     * update name, not the phoneNumber, phoneNumber must not be changed once created
     *
     * @param phoneNumber
     * @param customerDto
     * @return
     */
    @Override
    @Transactional
    public CustomerDto updateCustomer(String phoneNumber, CustomerDto customerDto) {
        Customer existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);
        BeanUtils.copyProperties(customerDto, existingCustomer, "phoneNumber");
        Customer updatedCustomer = customerRepository.saveAndFlush(existingCustomer);
        return CustomerDto.builder().
                name(updatedCustomer.getName()).
                phoneNumber(updatedCustomer.getPhoneNumber()).
                build();
    }

    @Override
    @Transactional
    public void deleteCustomer(String phoneNumber) {
        if (phoneNumber == null || StringUtils.isBlank(phoneNumber)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        customerRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<CustomerDto> getCustomerByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || StringUtils.isBlank(phoneNumber)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);

        // If customer is null, return an empty Optional
        if (customer == null) {
            return Optional.empty();
        }

        return Optional.of(
                CustomerDto.builder()
                        .name(customer.getName())
                        .phoneNumber(customer.getPhoneNumber())
                        .build());
    }

}