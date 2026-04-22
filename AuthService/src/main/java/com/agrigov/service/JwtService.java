package com.agrigov.service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.agrigov.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey accessKey;
    private final SecretKey refreshKey;
    private final long accessTtlMillis;
    private final long refreshTtlMillis;

    public JwtService(
            @Value("${jwt.access.secret}") String accessSecret,
            @Value("${jwt.refresh.secret}") String refreshSecret,
            @Value("${jwt.access.ttl-seconds:900}") long accessTtlSeconds,
            @Value("${jwt.refresh.ttl-seconds:2592000}") long refreshTtlSeconds) {

        this.accessKey =
                Keys.hmacShaKeyFor(accessSecret.getBytes(StandardCharsets.UTF_8));
        this.refreshKey =
                Keys.hmacShaKeyFor(refreshSecret.getBytes(StandardCharsets.UTF_8));
        this.accessTtlMillis = accessTtlSeconds * 1000;
        this.refreshTtlMillis = refreshTtlSeconds * 1000;
    }

    public long getAccessTokenTtlSeconds() {
        return accessTtlMillis / 1000;
    }

    public String generateAccessToken(User u) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(u.getEmail())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(accessTtlMillis)))
                .claim("uid", u.getId())
                .claim("role", u.getRole().name())
                .claim("typ", "access")
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(User u) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(u.getEmail())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusMillis(refreshTtlMillis)))
                .claim("uid", u.getId())
                .claim("typ", "refresh")
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
