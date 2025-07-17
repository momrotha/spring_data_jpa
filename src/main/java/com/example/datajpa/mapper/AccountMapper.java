package com.example.datajpa.mapper;

import com.example.datajpa.domain.Account;
import com.example.datajpa.dto.AccountRequest;
import com.example.datajpa.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {


    @Mapping(source = "accountType.type", target = "accountType")
    AccountResponse mapaccounttoResponse(Account account);

}
