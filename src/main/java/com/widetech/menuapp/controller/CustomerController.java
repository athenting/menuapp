package com.widetech.menuapp.controller;

import com.widetech.menuapp.dto.requests.CustomerDto;
import com.widetech.menuapp.dto.responses.RestResponse;
import com.widetech.menuapp.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public RestResponse<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto req) {
        CustomerDto customerEntity = customerService.createCustomer(req);
        return RestResponse.success(customerEntity);
    }

    @PutMapping("/{phoneNumber}")
    public RestResponse<CustomerDto> updateCustomer(@PathVariable String phoneNumber, @RequestBody CustomerDto customerDto) {
        return RestResponse.success(customerService.updateCustomer(phoneNumber, customerDto));
    }

    @DeleteMapping("/{phoneNumber}")
    public RestResponse<Void> deleteCustomer(@PathVariable String phoneNumber) {
        customerService.deleteCustomer(phoneNumber);
        //delete successfully, return with http code 204
        return RestResponse.noContent();
    }

    @GetMapping("/{phoneNumber}")
    public RestResponse<CustomerDto> getCustomerByPhoneNumber(@PathVariable String phoneNumber) {
        Optional<CustomerDto> customer = customerService.getCustomerByPhoneNumber(phoneNumber);
        return customer.map(RestResponse::success).orElseGet(RestResponse::notFound);
    }

}
