package com.example.datajpa.service;

import com.example.datajpa.dto.*;

import java.util.List;

public interface CustomerService {

    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);

    CustomerResponse findByPhoneNumber(String phoneNumber);
    List<AccountResponse> findAccountsByCustomerId(Integer customerId);
    AccountResponse findAccountByPhoneNumberAndActNo(String phoneNumber, String actNo);
    CustomerResponseDetail findCustomerWithAccountsByPhoneNumber(String phoneNumber);
    List<CustomerResponse> findAll();
    CustomerResponse createNew(CustomerRequest createcustomerRequest);

}