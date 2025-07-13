package com.example.datajpa.controller;

import com.example.datajpa.dto.*;
import com.example.datajpa.service.AccountService;
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
    private final AccountService accountService;

    @PatchMapping("/{phoneNumber}")
    public CustomerResponse updateCustomer(@PathVariable String phoneNumber, @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        return customerService.updateByPhoneNumber(phoneNumber, updateCustomerRequest);
    }
    @GetMapping("/{phoneNumber}")
    public CustomerResponse getCustomerByPhoneNumber(
            @PathVariable String phoneNumber){
        return customerService.findByPhoneNumber(phoneNumber);
    }


    @GetMapping("/accounts/{actNo}")
    public AccountResponse getAccountByActNo(@PathVariable String actNo) {
        return accountService.findByActNo(actNo);
    }

    @GetMapping("/{phoneNumber}/details")
    public CustomerResponseDetail getCustomerWithAccounts(@PathVariable String phoneNumber) {
        return customerService.findCustomerWithAccountsByPhoneNumber(phoneNumber);
    }

//    findaccountbycustomerID
    @GetMapping("/{customerId}")
    public List<AccountResponse> getAccountsByCustomerId(@PathVariable Integer customerId) {
        return customerService.findAccountsByCustomerId(customerId);
    }

    @GetMapping("/{phoneNumber}/{actNo}")
    public AccountResponse getAccountByPhoneNumberAndActNo(
            @PathVariable String phoneNumber,
            @PathVariable String actNo
    ) {
        return customerService.findAccountByPhoneNumberAndActNo(phoneNumber, actNo);
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
