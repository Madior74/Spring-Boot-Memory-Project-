package com.example.schooladmin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TokenCleanupService {
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    // Nettoie les tokens expirés toutes les 24 heures
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 24 heures en millisecondes
    public void cleanupExpiredTokens() {
        try {
            int deletedCount = refreshTokenService.deleteExpiredTokens();
            System.out.println("Nettoyage automatique : " + deletedCount + " tokens expirés supprimés");
        } catch (Exception e) {
            System.err.println("Erreur lors du nettoyage des tokens expirés : " + e.getMessage());
        }
    }
} 