package com.example.datajpa.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        Integer id,
        String fullName,
        String gender,
        String phoneNumber,
        String nationalCardId,
        String email
) {
}
