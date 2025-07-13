package com.example.datajpa.mapper;

import com.example.datajpa.domain.Account;
import com.example.datajpa.dto.AccountRequest;
import com.example.datajpa.dto.AccountResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    Account toEntity(AccountRequest accountRequest);
    AccountResponse mapaccounttoResponse(Account account);
}
