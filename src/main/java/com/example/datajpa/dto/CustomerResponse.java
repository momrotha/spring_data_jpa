package com.example.datajpa.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String fullName,
        String gender,
        String phoneNumber,
        String email
) {
}
