package com.example.datajpa.controller;

import com.example.datajpa.service.KYCService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/kyc")
@RequiredArgsConstructor
public class KYCController {

    private final KYCService kycService;

    @PutMapping("/customers/{nationalCardId}/verify")
    ResponseEntity<String> verify(@PathVariable String nationalCardId) {
        kycService.verifyCustomerByNationalCardId(nationalCardId);
        return ResponseEntity.ok().body("Customer has been verified successfully");
    }

}
