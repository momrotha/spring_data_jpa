package com.example.datajpa.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(unique = true, length = 100)
    private String email;
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

    @OneToOne(mappedBy = "customer", optional = false)
    private KYC kyc;

    @ManyToOne
    private CustomerSegment customerSegment;
}
