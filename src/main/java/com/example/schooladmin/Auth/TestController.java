package com.example.schooladmin.Auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            Map<String, Object> profile = Map.of(
                "message", "Profil récupéré avec succès",
                "user", authentication.getName(),
                "authorities", authentication.getAuthorities().toString(),
                "timestamp", System.currentTimeMillis()
            );
            
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Non authentifié"));
        }
    }
    
    @GetMapping("/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        return ResponseEntity.ok(Map.of(
            "message", "Pong",
            "timestamp", System.currentTimeMillis(),
            "status", "OK"
        ));
    }
} 