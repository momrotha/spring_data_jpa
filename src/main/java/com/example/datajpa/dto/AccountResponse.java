package com.example.datajpa.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record AccountResponse(
        String actNo,
        BigDecimal balance,
        BigDecimal overLimit

) {
}
