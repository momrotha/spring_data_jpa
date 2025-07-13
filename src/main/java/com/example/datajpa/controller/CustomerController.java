package com.example.datajpa.controller;

import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;
import com.example.datajpa.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("/{phoneNumber}")
    public CustomerResponse updateCustomer(@PathVariable String phoneNumber, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.updateByPhoneNumber(phoneNumber, updateCustomerRequest);
    }
    @GetMapping("/{phoneNumber}")
    public CustomerResponse getCustomerByPhoneNumber(
            @PathVariable String phoneNumber){
        return customerService.findByPhoneNumber(phoneNumber);
    }


//    @GetMapping("/customer/{customerId}")
//    public AccountResponse[] getByCustomer(@PathVariable Integer customerId) {
//        return customerService.findByCustomer(customerId);
//    }


    @GetMapping("/{phoneNumber}/accounts")
    public CustomerResponse findAccountByCustomer(
            @PathVariable String phoneNumber){
        return customerService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        return customerService.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createNew(@RequestBody CustomerRequest customerRequest) {
        return customerService.createNew(customerRequest);
    }
}
