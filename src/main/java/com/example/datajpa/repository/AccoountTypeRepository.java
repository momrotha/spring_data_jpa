package com.example.datajpa.repository;

import com.example.datajpa.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccoountTypeRepository extends JpaRepository<AccountType,Integer> {

    Optional<AccountType> findByType(String  type);
}
