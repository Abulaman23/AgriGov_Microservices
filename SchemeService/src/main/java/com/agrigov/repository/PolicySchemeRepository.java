package com.agrigov.repository;

import com.agrigov.model.PolicyScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PolicySchemeRepository extends JpaRepository<PolicyScheme, Long> {
    Optional<PolicyScheme> findByTitleIgnoreCase(String title);
    boolean existsByTitleIgnoreCase(String title);
}