package com.agrigov.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrigov.model.RefreshToken;
import com.agrigov.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	Optional<RefreshToken> findByToken(String token);

	void deleteByToken(String token);

	void deleteAllByUser(User user);
}
