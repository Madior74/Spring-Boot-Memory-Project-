package com.example.schooladmin.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.schooladmin.security.JwtService;
import com.example.schooladmin.security.RefreshTokenService;
import com.example.schooladmin.utilisateur.CustomUserDetailsService;
import com.example.schooladmin.admin.AdminRepository;
import com.example.schooladmin.etudiant.candiddat.CandidatRepository;
import com.example.schooladmin.professeur.ProfesseurRepository;
import com.example.schooladmin.utilisateur.Utilisateur;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private CandidatRepository candidatPreInscritRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("JWT Filter - Requête entrante : {}", request.getRequestURI());

        String requestURI = request.getRequestURI();

        // Ignorer les routes publiques
        List<String> publicRoutes = List.of("/api/auth/login", "/api/auth/register", "/api/auth/refresh", "/api/auth/logout", "/error");
        if (publicRoutes.stream().anyMatch(requestURI::contains)) {
            logger.info("Ignorer le filtre JWT pour : {}", requestURI);
            filterChain.doFilter(request, response);
            return;

        }

        String header = request.getHeader("Authorization");
        String token = null;
        String email = null;

        if (header != null && header.startsWith("Bearer ")) {
            // Sinon, tu pourrais extraire "earer token..." à cause de l'oubli de l'espace.
            token = header.substring(7); // Retire "Bearer"
            logger.info("Token extrait: {}", token.substring(0, Math.min(50, token.length())) + "...");
            try {
                email = jwtService.extractUsername(token); // Extrait l'email
                logger.info("Email extrait du token: {}", email);
            } catch (ExpiredJwtException e) {
                logger.warn("Token expiré, tentative de refresh automatique");
                // Essayer de rafraîchir automatiquement le token
                String refreshToken = request.getHeader("Refresh-Token");
                if (refreshToken != null) {
                    try {
                        // Logique de refresh directement dans le filtre
                        var refreshResult = refreshTokenDirectly(refreshToken);
                        String newAccessToken = (String) refreshResult.get("accessToken");
                        String newRefreshToken = (String) refreshResult.get("refreshToken");
                        
                        // Ajouter les nouveaux tokens aux headers de réponse
                        response.setHeader("New-Access-Token", newAccessToken);
                        response.setHeader("New-Refresh-Token", newRefreshToken);
                        response.setHeader("Token-Refreshed", "true");
                        
                        // Utiliser le nouveau token pour l'authentification
                        token = newAccessToken;
                        email = jwtService.extractUsername(newAccessToken);
                        logger.info("Token rafraîchi avec succès pour: {}", email);
                    } catch (Exception refreshException) {
                        logger.warn("Échec du refresh automatique", refreshException);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expiré et refresh échoué");
                        return;
                    }
                } else {
                    logger.warn("Token expiré sans refresh token");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expiré");
                    return;
                }
            } catch (JwtException e) {
                logger.warn("Erreur lors de l'extraction du token", e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token invalide");
                return;
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                logger.info("UserDetails chargées pour {}: {}", email, userDetails.getUsername());
                logger.info("Authorities chargées : {}", userDetails.getAuthorities());

                logger.info("Validation du token pour l'utilisateur: {}", email);
                if (jwtService.validateToken(token, userDetails)) {
                    logger.info("Token validé avec succès");
                    // 🔽 EXTRAIRE LE RÔLE DU TOKEN
                    String role = jwtService.extractClaim(token, claims -> claims.get("role", String.class));
                    logger.info("Rôle extrait du token : {}", role);
                    if (role == null || role.isEmpty()) {
                        logger.warn("Aucun rôle trouvé dans le token pour l'utilisateur : {}", email);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token sans rôle");
                        return;
                    }

                    // 🔽 CONVERTIR EN AUTHORITIES
                    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, authorities);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentification mise à jour dans le contexte de sécurité pour : {} avec rôles : {}",
                            email, authorities);
                    logger.info("SecurityContext authentication: {}", SecurityContextHolder.getContext().getAuthentication());
                } else {
                    logger.warn("Token non valide pour l'utilisateur : {}", email);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token non valide");
                    return;
                }
            } catch (Exception e) {
                logger.error("Erreur lors du traitement de l'authentification : {}", e.getMessage(), e);
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Erreur d'authentification");
                return;
            }
        }

        // ⚠️ IMPORTANT : Continuer la chaîne de filtres
        filterChain.doFilter(request, response);
    }

    // Méthode pour rafraîchir le token directement dans le filtre
    private Map<String, Object> refreshTokenDirectly(String refreshToken) {
        var token = refreshTokenService.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Refresh token non trouvé"));
        
        refreshTokenService.verifyExpiration(token);
        
        // Récupérer l'utilisateur basé sur l'email et le type
        Utilisateur utilisateur = findUserByEmailAndType(token.getUserEmail(), token.getUserType());
        String newAccessToken = jwtService.generateToken(utilisateur);
        String newRefreshToken = refreshTokenService.createRefreshToken(utilisateur.getEmail(), utilisateur.getRole().name()).getToken();
        
        // Supprimer l'ancien refresh token
        refreshTokenService.deleteRefreshToken(refreshToken);
        
        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", newAccessToken);
        result.put("refreshToken", newRefreshToken);
        result.put("tokenType", "Bearer");
        result.put("user", Map.of(
                "email", utilisateur.getEmail(),
                "role", utilisateur.getRole().name(),
                "nom", utilisateur.getNom(),
                "prenom", utilisateur.getPrenom()));
        
        return result;
    }

    private Utilisateur findUserByEmailAndType(String email, String userType) {
        switch (userType) {
            case "ROLE_ADMIN":
                return adminRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Admin non trouvé"));
            case "ROLE_PROFESSEUR":
                return professeurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
            case "ROLE_ETUDIANT":
                return candidatPreInscritRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
            default:
                throw new RuntimeException("Type d'utilisateur inconnu: " + userType);
        }
    }
}