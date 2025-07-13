package com.example.datajpa.dto;

import java.util.List;

public record CustomerResponseDetail(
        String fullName,
        String gender,
        List<AccountInfo> accounts
) {
    public record AccountInfo(String actNo) {}
}
