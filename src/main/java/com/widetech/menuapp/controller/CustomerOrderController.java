package com.widetech.menuapp.controller;

import com.widetech.menuapp.dao.entity.Customer;
import com.widetech.menuapp.dto.requests.CustomerDto;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerOrderController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public RestResponse<Customer> createCustomer(@Valid @RequestBody CustomerDto req) {

        Customer customer = new Customer();
        customer.setName(req.getName());
        customer.setPhoneNumber(req.getPhoneNumber());

        Customer customerEntity = customerService.createCustomer(customer);

        return RestResponse.success(customerEntity);
    }

    @PutMapping("/{phoneNumber}")
    public Customer updateCustomer(@PathVariable String phoneNumber, @RequestBody CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        return customerService.updateCustomer(phoneNumber, customer);
    }

    @DeleteMapping("/{phoneNumber}")
    public RestResponse<Void> deleteCustomer(@PathVariable String phoneNumber) {
        customerService.deleteCustomer(phoneNumber);
        //delete successfully, return with http code 204
        return RestResponse.noContent();
    }

    @GetMapping("/{phoneNumber}")
    public RestResponse<Customer> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<Customer> customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        return customer.map(RestResponse::success).orElseGet(RestResponse::notFound);
    }

}
