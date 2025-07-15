package com.example.datajpa.repository;

import com.example.datajpa.domain.KYC;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KYCRepository extends JpaRepository<KYC, String> {

    boolean existsByNationalCardId(String nationalCardId);

    Optional<KYC> findByNationalCardId(String nationalCardId);

}
