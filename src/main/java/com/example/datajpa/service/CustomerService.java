package com.example.datajpa.service;

import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
import com.example.datajpa.dto.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {

    CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest);

    CustomerResponse findByPhoneNumber(String phoneNumber);

    List<CustomerResponse> findAll();
    CustomerResponse createNew(CustomerRequest createcustomerRequest);

}
