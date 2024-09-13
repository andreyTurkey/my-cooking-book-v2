package com.example.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.application.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
