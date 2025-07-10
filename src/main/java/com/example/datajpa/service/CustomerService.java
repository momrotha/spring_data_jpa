package com.example.datajpa.service;

import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> findAll();
    CustomerResponse createNew(CustomerRequest createcustomerRequest);

}
