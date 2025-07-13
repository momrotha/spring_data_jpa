package com.example.datajpa.controller;

import com.example.datajpa.dto.AccountRequest;
import com.example.datajpa.dto.AccountResponse;
import com.example.datajpa.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AccountResponse createAccount(@RequestBody AccountRequest accountRequest) {
        return accountService.createAccount(accountRequest);
    }

    @GetMapping
    public AccountResponse[] getAll() {
        return accountService.findAll();
    }

    @GetMapping("/{actNo}")
    public AccountResponse getByActNo(@PathVariable String actNo) {
        return accountService.findByActNo(actNo);
    }

    @GetMapping("/customer/{customerId}/accounts")
    public AccountResponse[] getAccountsByCustomer(@PathVariable Integer customerId) {
        return accountService.findByCustomer(customerId);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{actNo}")
    public void delete(@PathVariable String actNo) {
        accountService.deleteByActNo(actNo);
    }


    @PatchMapping("/{actNo}")
    public AccountResponse update(@PathVariable String actNo, @RequestBody AccountRequest accountRequest) {
        return accountService.updateAccount(actNo, accountRequest);
    }


    @PutMapping("/disable/{actNo}")
    public AccountResponse disable(@PathVariable String actNo) {
        return accountService.disableAccount(actNo);
    }

}