package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Account;
import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.*;
import com.example.datajpa.mapper.AccountMapper;
import com.example.datajpa.mapper.CustomerMapper;
import com.example.datajpa.repository.AccountRepository;
import com.example.datajpa.repository.CustomerRepository;
import com.example.datajpa.service.CustomerService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private  final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public CustomerResponse updateByPhoneNumber(String phoneNumber, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number not found"));
        customerMapper.mapCustomertoCustomerPartially(updateCustomerRequest, customer);
        customer = customerRepository.save(customer);
        return customerMapper.mapCustomerToCustomerResponse(customer);
    }

    @Override
    public CustomerResponse findByPhoneNumber(String phoneNumber) {
        return customerRepository
                .findByPhoneNumber(phoneNumber)
                .map(customerMapper::mapCustomerToCustomerResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Phone number not found"));
    }

    //    CustomerResponseDetail
    @Override
    public CustomerResponseDetail findCustomerWithAccountsByPhoneNumber(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        List<Account> accounts = accountRepository.findByCustomer(customer);

        List<CustomerResponseDetail.AccountInfo> accountInfos = accounts.stream()
                .map(account -> new CustomerResponseDetail.AccountInfo(account.getActNo()))
                .toList();

        return new CustomerResponseDetail(
                customer.getFullName(),
                customer.getGender(),
                accountInfos
        );
    }

    //    findaccountbyid
    @Override
    public List<AccountResponse> findAccountsByCustomerId(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        List<Account> accounts = accountRepository.findByCustomer(customer);

        return accounts.stream()
                .map(accountMapper::mapaccounttoResponse)
                .toList();
    }



    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::mapCustomerToCustomerResponse)
                .toList();
    }

    @Override
    public CustomerResponse createNew(CustomerRequest createCustomerRequest) {
        if (customerRepository.existsByEmail(createCustomerRequest.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");

        Customer customer = customerMapper.fromCreateCustomerRequest(createCustomerRequest);
        customer.setIsDeleted(false);

        log.info("Customer ID before save: {}", customer.getId());
        customer = customerRepository.save(customer);
        log.info("Customer ID after save: {}", customer.getId());


        return customerMapper.mapCustomerToCustomerResponse(customer);
    }


    @Override
    public AccountResponse findAccountByPhoneNumberAndActNo(String phoneNumber, String actNo) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Account account = accountRepository.findByActNoAndCustomer(actNo, customer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        return accountMapper.mapaccounttoResponse(account);
    }
}