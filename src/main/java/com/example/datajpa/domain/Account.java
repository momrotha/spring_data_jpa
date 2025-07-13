package com.example.datajpa.domain;

import com.example.datajpa.domain.AccountType;
import com.example.datajpa.domain.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String actNo;
    private BigDecimal balance;
    private BigDecimal overLimit;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "acc_type_id")
    private AccountType accountType;

    @Version
    private Integer version;
}
