package com.example.datajpa.dto;

import java.time.LocalDate;

public record CustomerRequest(
        String fullName,
        String gender,
        LocalDate dob,
        String email,
        String phoneNumber,
        String remark,
        String nationalCardId,
        String customerSegment
) {
}
