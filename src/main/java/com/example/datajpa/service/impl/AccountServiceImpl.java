package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Account;
import com.example.datajpa.domain.AccountType;
import com.example.datajpa.domain.Customer;
import com.example.datajpa.dto.AccountRequest;
import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.mapper.AccountMapper;
import com.example.datajpa.repository.AccoountTypeRepository;
import com.example.datajpa.repository.AccountRepository;
import com.example.datajpa.repository.CustomerRepository;
import com.example.datajpa.service.AccountService;
import com.example.datajpa.util.CurrencyUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final AccoountTypeRepository accoountTypeRepository;
    private final CustomerRepository customerRepository;

    @Override
    public AccountResponse createNew(AccountRequest accountRequest) {
        Account account = new Account();
//        validation account type
        AccountType accountType = accoountTypeRepository
                .findByType(accountRequest.accountType())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Account Type Not Found"));

//        validation customer phone number
        Customer customer = customerRepository
                .findCustomerByPhoneNumber(accountRequest.phoneNumber())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer phone number Not Found"));

        switch (accountRequest.actCurrency()) {
            case CurrencyUtil.USD -> {
                if (accountRequest.balance().compareTo(BigDecimal.valueOf(10))<0){
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must than 10 USD");
                }
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")){
                    account.setOverLimit(BigDecimal.valueOf(5000));
                }else if (customer.getCustomerSegment().getSegment().equals("SILVER")){
                    account.setOverLimit(BigDecimal.valueOf(10000));
                } else if (customer.getCustomerSegment().getSegment().equals("GOLD")){
                    account.setOverLimit(BigDecimal.valueOf(50000));
                }
            }
            case CurrencyUtil.KHR -> {
                if (accountRequest.balance().compareTo(BigDecimal.valueOf(40000)) < 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Balance must be greater than 40,000 KHR");
                }
                if (customer.getCustomerSegment().getSegment().equals("REGULAR")) {
                    account.setOverLimit(BigDecimal.valueOf(5000 * 4000));
                } else if (customer.getCustomerSegment().getSegment().equals("SILVER")) {
                    account.setOverLimit(BigDecimal.valueOf(10000 * 4000));
                } else {
                    account.setOverLimit(BigDecimal.valueOf(50000 * 4000));
                }
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Currency is not supported");

        }
//        validation account number
        if (accountRequest.actNo()!=null){
            if (accountRepository.existsByActNo(accountRequest.actNo())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Account Number %s Already Exists",accountRequest.actNo()));

            }
            account.setActNo(accountRequest.actNo());
        }else {
            String actNo;
            do {
                actNo = String.format("%09d",new Random().nextInt(1_000_000_000));
            }while (accountRepository.existsByActNo(actNo));
            account.setActNo(actNo);
        }

//        set data logic
        account.setActName(accountRequest.actName());
        account.setActCurrency(accountRequest.actCurrency().name());
        account.setBalance(accountRequest.balance());
        account.setIsHide(false);
        account.setCustomer(customer);
        account.setAccountType(accountType);
        account = accountRepository.save(account);

        return accountMapper.mapaccounttoResponse(account);
    }

    @Override
    public AccountResponse[] findAll() {
        return accountRepository.findAll().stream()
                .map(accountMapper::mapaccounttoResponse)
                .toArray(AccountResponse[]::new);
    }

    @Override
    public AccountResponse findByActNo(String actNo) {
        return accountRepository.findByActNo(actNo)
                .map(accountMapper::mapaccounttoResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
    }
    @Override
    public AccountResponse[] findByCustomer(Integer customerId) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);
        return accounts.stream()
                .map(accountMapper::mapaccounttoResponse)
                .toArray(AccountResponse[]::new);
    }

    @Override
    public void deleteByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountRepository.delete(account);
    }

    @Override
    public AccountResponse updateAccount(String actNo, AccountRequest accountRequest) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setBalance(accountRequest.balance());
//        account.setOverLimit(accountRequest.overLimit());
        return accountMapper.mapaccounttoResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse disableAccount(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        if (account.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account already disabled");
        }

        account.setIsDeleted(true);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.mapaccounttoResponse(savedAccount);
    }

}