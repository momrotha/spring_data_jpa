package com.example.datajpa.dto;

import com.example.datajpa.util.CurrencyUtil;

import java.math.BigDecimal;

public record AccountRequest(
        String actNo,
        String actName,
        CurrencyUtil actCurrency,
        BigDecimal balance,
        String accountType,
        String phoneNumber
) {
}
