package com.example.datajpa.init;

import com.example.datajpa.domain.AccountType;
import com.example.datajpa.repository.AccoountTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountTypeInitialize {
    private final AccoountTypeRepository accoountTypeRepository;

    @PostConstruct
    public void init(){
        if (accoountTypeRepository.count() == 0) {
            AccountType payroll = new AccountType();
            payroll.setType("PAYROLL");
            payroll.setIsDeleted(false);

            AccountType saving = new AccountType();
            saving.setType("SAVING");
            saving.setIsDeleted(false);

            AccountType junior = new AccountType();
            junior.setType("JUNIOR");
            junior.setIsDeleted(false);

            accoountTypeRepository.saveAll(List.of(payroll,saving,junior));

        }

    }
}
