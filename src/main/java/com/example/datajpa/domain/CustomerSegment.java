package com.example.datajpa.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@NoArgsConstructor
@Data
@Table(name = "customer_segment")
public class CustomerSegment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true, length = 100)
    private String segment;
//    private String type;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private boolean is_deleted;


    @OneToMany(mappedBy = "customerSegment", fetch = FetchType.EAGER)
    private List<Customer> customers;

}
