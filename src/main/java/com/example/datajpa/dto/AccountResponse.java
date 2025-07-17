package com.example.datajpa.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record AccountResponse(
        String actNo,
        String actName,
        String actCurrency,
        BigDecimal balance,
        Boolean isHide,
        String accountType
) {}

