package com.example.datajpa.repository;

import com.example.datajpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAllByIsDeletedFalse();

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Customer> findCustomerByPhoneNumberAndIsDeletedFalse(String phoneNumber);

    @Modifying
    @Query(value =
            """
            UPDATE Customer c
            SET c.isDeleted = true
            WHERE c.phoneNumber = ?1
            """)
    void disableByPhoneNumber(String phoneNumber);

    Optional<Customer> findCustomerByPhoneNumber(String phoneNumber);


}
