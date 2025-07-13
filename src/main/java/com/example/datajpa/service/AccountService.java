package com.example.datajpa.service;

import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.AccountRequest;
import com.example.datajpa.dto.AccountResponse;

public interface AccountService {

    AccountResponse createAccount(AccountRequest accountRequest);
    AccountResponse[] findAll();
    AccountResponse findByActNo(String actNo);
    AccountResponse[] findByCustomer(Integer customerId);
    void deleteByActNo(String actNo);
    AccountResponse updateAccount(String actNo, AccountRequest dto);
    AccountResponse disableAccount(String actNo);
//    AccountResponse findByCustomer(Customer customer);
}
