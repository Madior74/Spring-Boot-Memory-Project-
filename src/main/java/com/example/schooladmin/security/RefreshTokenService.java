package com.example.schooladmin.security;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.schooladmin.exception.CustomException;

@Service
public class RefreshTokenService {
    
    @Value("${jwt.refresh.expiration:604800}") // 7 jours par défaut
    private Long refreshTokenDurationMs;
    
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    
    public RefreshToken createRefreshToken(String userEmail, String userType) {
        RefreshToken refreshToken = new RefreshToken();
        
        refreshToken.setUserEmail(userEmail);
        refreshToken.setUserType(userType);
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());
        
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
    
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
    
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new CustomException("Refresh token expiré. Veuillez vous reconnecter.");
        }
        return token;
    }
    
    public void deleteByUserEmail(String userEmail) {
        refreshTokenRepository.deleteByUserEmail(userEmail);
    }
    
    public int deleteExpiredTokens() {
        return refreshTokenRepository.deleteExpiredTokens(Instant.now());
    }
    
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
            .ifPresent(refreshTokenRepository::delete);
    }
} 