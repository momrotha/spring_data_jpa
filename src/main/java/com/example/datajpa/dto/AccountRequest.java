package com.example.datajpa.dto;

import java.math.BigDecimal;

public record AccountRequest(
        String actNo,
        BigDecimal balance,
        BigDecimal overLimit,
        Boolean isDeleted
) {
}
