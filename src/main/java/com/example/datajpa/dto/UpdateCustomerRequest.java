package com.example.datajpa.dto;

public record UpdateCustomerRequest(
        String fullName,
        String gender,
        String remark
) {
}
