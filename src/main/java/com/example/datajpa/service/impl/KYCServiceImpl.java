package com.example.datajpa.service.impl;

import com.example.datajpa.domain.KYC;
import com.example.datajpa.repository.KYCRepository;
import com.example.datajpa.service.KYCService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KYCServiceImpl implements KYCService {
    private final KYCRepository kycRepository;


    @Override
    public void verifyCustomerByNationalCardId(String nationalCardId) {
        KYC kyc = kycRepository
                .findByNationalCardId(nationalCardId)
                .orElseThrow(()->new EntityNotFoundException("NationalCardId not found"));

        kyc.setIsVerified(true);
        kycRepository.save(kyc);
    }
}

