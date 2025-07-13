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

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public AccountResponse createAccount(AccountRequest accountRequest) {
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
        return accountRepository.findAll().stream()
                .filter(account -> account.getCustomer().getId().equals(customerId))
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
    public AccountResponse updateAccount(String actNo, AccountRequest dto) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        account.setBalance(dto.balance());
        account.setOverLimit(dto.overLimit());
        return accountMapper.mapaccounttoResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse disableAccount(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));

        account.setIsDeleted(true);

        Account savedAccount = accountRepository.save(account);
        return accountMapper.mapaccounttoResponse(savedAccount);
    }
}