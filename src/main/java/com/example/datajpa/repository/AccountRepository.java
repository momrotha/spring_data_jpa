package com.example.datajpa.repository;


import com.example.datajpa.domain.Account;
import com.example.datajpa.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByActNo(String actNo);
    Optional<Account> findFirstByCustomer(Customer customer);
    List<Account> findByCustomerId(Integer customerId);


    boolean existsByActNo(String actNo);

    List<Account> findByCustomer(Customer customer);

}
