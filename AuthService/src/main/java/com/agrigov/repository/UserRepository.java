package com.agrigov.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrigov.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
}
