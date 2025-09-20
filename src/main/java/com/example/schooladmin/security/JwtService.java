package com.example.schooladmin.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.schooladmin.utilisateur.Utilisateur;


@Service
public class JwtService {

        private final String jwtSecret = "jvPeFfP7WuVToKYMZabH9hE8lGqWXNtLZUdJXsRzj3mQxgA=";

    // ⏱️ Durée de validité du token : 1 h 
    private final long JWT_TOKEN_VALIDITY = 360000000; 

    // Extraire le nom d'utilisateur (email) depuis le token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraire une claim spécifique
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


   public String generateToken(Utilisateur utilisateur) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("role", utilisateur.getRole().name()); // Simple enum
    claims.put("nom", utilisateur.getNom());
    claims.put("prenom", utilisateur.getPrenom());

    return createToken(claims, utilisateur.getEmail()); // Toujours l'email comme identifiant
}


    // Génère un token à partir de l'email de l'utilisateur et de ses rôl
    // Construit le token JWT avec les claims et la durée de vie
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Valide si le token appartient à l'utilisateur et n’est pas expiré
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Vérifie si le token est expiré
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extraire la date d'expiration
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extraire toutes les claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Récupère la clé de signature
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Méthode pour rafraîchir le token (à implémenter)
    public String refreshToken(String oldToken) {
    try {
        Claims claims = extractAllClaims(oldToken);
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    } catch (ExpiredJwtException e) {
        Claims claims = e.getClaims(); // récupère même les claims d'un token expiré
        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }
}

}