package com.example.datajpa.mapper;

import com.example.datajpa.domain.Account;
import com.example.datajpa.dto.AccountRequest;
import com.example.datajpa.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity(AccountRequest accountRequest);

    @Mapping(source = "isDeleted", target = "isDeleted")
    AccountResponse mapaccounttoResponse(Account account);
}
