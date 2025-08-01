package com.example.datajpa.repository;

import com.example.datajpa.domain.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media,Integer> {
}
