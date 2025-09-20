package com.example.schooladmin.security;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    
    Optional<RefreshToken> findByToken(String token);
    
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.userEmail = ?1")
    int deleteByUserEmail(String userEmail);
    
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.expiryDate < ?1")
    int deleteExpiredTokens(java.time.Instant now);
} 