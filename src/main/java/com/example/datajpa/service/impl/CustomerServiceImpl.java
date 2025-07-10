package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.CustomerRequest;
import com.example.datajpa.dto.CustomerResponse;
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

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> CustomerResponse.builder()
                        .fullName(customer.getFullName())
                        .gender(customer.getGender())
                        .email(customer.getEmail())
                        .build()
                )
                .toList();
    }

    @Override
    public CustomerResponse createNew(CustomerRequest createCustomerRequest) {
        if (customerRepository.existsByEmail(createCustomerRequest.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");

        if (customerRepository.existsByPhoneNumber(createCustomerRequest.phoneNumber()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Phone number already exists");

        Customer customer = new Customer();
        customer.setFullName(createCustomerRequest.fullName());
        customer.setGender(createCustomerRequest.gender());
        customer.setEmail(createCustomerRequest.email());
        customer.setPhoneNumber(createCustomerRequest.phoneNumber());
        customer.setRemark(createCustomerRequest.remark());
        customer.setIsDeleted(false);

        log.info("Customer ID before save: {}", customer.getId());
        customer  = customerRepository.save(customer);
        log.info("Customer ID after save: {}", customer.getId());



        return CustomerResponse.builder()
                .fullName(customer.getFullName())
                .gender(customer.getGender())
                .email(customer.getEmail())
                .build();
    }
}
