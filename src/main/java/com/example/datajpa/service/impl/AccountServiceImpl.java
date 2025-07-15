package com.example.datajpa.service.impl;

import com.example.datajpa.domain.Account;
import com.example.datajpa.dto.AccountRequest;
import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.mapper.AccountMapper;
import com.example.datajpa.repository.AccountRepository;
import com.example.datajpa.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
        if (accountRepository.existsByActNo(accountRequest.actNo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Account already exists with actNo: " + accountRequest.actNo());
        }
        Account account = accountMapper.toEntity(accountRequest);
        account.setIsDeleted(false);

        Account savedAccount = accountRepository.save(account);

        return accountMapper.mapaccounttoResponse(savedAccount);
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
        account.setOverLimit(accountRequest.overLimit());
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