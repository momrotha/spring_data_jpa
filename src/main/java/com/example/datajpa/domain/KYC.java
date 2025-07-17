package com.example.datajpa.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity

public class KYC {
    @Id
    private Integer id;
    @Column(unique = true, nullable = false,length = 12)
    private String nationalCardId;
    @Column(nullable = false)
    private Boolean isVerified;
    @Column(nullable = false)
    private Boolean isDeleted;

    @OneToOne(optional = false)
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
