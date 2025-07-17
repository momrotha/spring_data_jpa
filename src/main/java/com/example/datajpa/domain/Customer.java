package com.example.datajpa.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false, length = 10)
    private String gender;

    @Column(nullable = false)
    private LocalDate dob;

    @Column(unique = true, length = 100)
    private String email;

    @Column(length = 100)
    private String address;
    @Column(length = 50)
    private String cityOrProvince;
    @Column(length = 50)
    private String country;
    @Column(length = 50)
    private String zipCode;

    @Column(length = 50)
    private String employmentType;
    @Column(length = 50)
    private String position;
    @Column(length = 50)
    private String companyName;
    @Column(length = 50)
    private String mainSourceOfIncome;
    @Column(length = 50)
    private BigDecimal monthlyIncomeRange;

    @Column(unique = true, length = 15)
    private String phoneNumber;
    @Column(columnDefinition = "TEXT")
    private String remark;

    @Column(nullable = false)
    private Boolean isDeleted;
//use mappedBy for tell it mean relationship hx trov yk pi na kom create new
    @OneToMany(mappedBy = "customer")
//    customer has much accounts  use has -a (use object)
    private List<Account> accounts;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private KYC kyc;

    @ManyToOne
    private CustomerSegment customerSegment;
}
