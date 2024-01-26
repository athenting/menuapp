package com.widetech.menuapp.controller;

import com.widetech.menuapp.dao.entity.Customer;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerOrderController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{phoneNumber}")
    public Customer updateCustomer(@PathVariable String phoneNumber, @RequestBody Customer customer) {
        return customerService.updateCustomer(phoneNumber, customer);
    }

    @DeleteMapping("/{phoneNumber}")
    public RestResponse<Void> deleteCustomer(@PathVariable String phoneNumber) {
        customerService.deleteCustomer(phoneNumber);
        return RestResponse.noContent();
    }

    @GetMapping("/{phoneNumber}")
    public RestResponse<Customer> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<Customer> customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        return customer.map(RestResponse::success).orElseGet(RestResponse::notFound);
    }

}
