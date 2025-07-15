package com.example.datajpa.repository;

import com.example.datajpa.domain.CustomerSegment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerSegmentRepository extends JpaRepository<CustomerSegment, Integer> {

    Optional<CustomerSegment> getCustomerSegmentBySegment(String segment);

}
