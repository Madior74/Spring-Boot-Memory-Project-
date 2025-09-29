package com.example.schooladmin.Auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.admin.Admin;
import com.example.schooladmin.admin.AdminRepository;
import com.example.schooladmin.etudiant.candiddat.Candidat;
import com.example.schooladmin.etudiant.candiddat.CandidatRepository;
import com.example.schooladmin.professeur.Professeur;
import com.example.schooladmin.professeur.ProfesseurRepository;
import com.example.schooladmin.security.JwtService;
import com.example.schooladmin.security.RefreshTokenService;
import com.example.schooladmin.security.RefreshToken;
import com.example.schooladmin.utilisateur.Utilisateur;

@Service
public class AuthService {

    @Autowired
    private CandidatRepository candidatPreInscritRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private AuthenticationManager authenticationManager;
public Map<String, Object> login(String email, String password) {
    Utilisateur utilisateur = null;

    // Cherche dans les 3 repositories
    Optional<Candidat> candidatOpt = candidatPreInscritRepository.findByEmail(email);
    Optional<Professeur> professeurOpt = professeurRepository.findByEmail(email);
    Optional<Admin> adminOpt = adminRepository.findByEmail(email);

    if (candidatOpt.isPresent()) {
        utilisateur = candidatOpt.get();
    } else if (professeurOpt.isPresent()) {
        utilisateur = professeurOpt.get();
    } else if (adminOpt.isPresent()) {
        utilisateur = adminOpt.get();
    }

    // Si aucun utilisateur trouvé
    if (utilisateur == null) {
        throw new BadCredentialsException("Identifiants invalides");
    }

    // Vérifie le mot de passe (en supposant que le mot de passe est encodé avec BCrypt)
    if (!passwordEncoder.matches(password, utilisateur.getPassword())) {
        throw new BadCredentialsException("Identifiants invalides");
    }

    // Génération des tokens
    String accessToken = jwtService.generateToken(utilisateur);
    String refreshToken = refreshTokenService
            .createRefreshToken(utilisateur.getEmail(), utilisateur.getRole().name()).getToken();

    Map<String, Object> result = new HashMap<>();
    result.put("message", "Connexion réussie");
    result.put("accessToken", accessToken);
    result.put("refreshToken", refreshToken);
    result.put("tokenType", "Bearer");
    result.put("user", Map.of(
            "email", utilisateur.getEmail(),
            "role", utilisateur.getRole().name(),
            "nom", utilisateur.getNom(),
            "prenom", utilisateur.getPrenom()
    ));

    return result;
}

    public Map<String, Object> refreshToken(String refreshToken) {
        RefreshToken token = refreshTokenService.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Refresh token non trouvé"));

        refreshTokenService.verifyExpiration(token);

        // Récupérer l'utilisateur basé sur l'email et le type
        Utilisateur utilisateur = findUserByEmailAndType(token.getUserEmail(), token.getUserType());
        String newAccessToken = jwtService.generateToken(utilisateur);
        String newRefreshToken = refreshTokenService
                .createRefreshToken(utilisateur.getEmail(), utilisateur.getRole().name()).getToken();

        // Supprimer l'ancien refresh token
        refreshTokenService.deleteRefreshToken(refreshToken);

        Map<String, Object> result = new HashMap<>();
        result.put("message", "Token rafraîchi avec succès");
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

    public void logout(String refreshToken) {
        refreshTokenService.deleteRefreshToken(refreshToken);
    }

    // public Utilisateur authenticate(String email, String password) {
    // Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
    // .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

    // if (!utilisateur.getPassword().equals(password)) {
    // throw new RuntimeException("Mot de passe incorrect");
    // }

    // return utilisateur;
    // }

    // public Map<String, Object> login(String email, String password) {
    // authenticationManager.authenticate(
    // new UsernamePasswordAuthenticationToken(email, password));

    // Utilisateur user = userRepository.findByEmailWithRoles(email)
    // .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

    // String token = jwtService.generateToken(user); // ⬅️ Utilise la version avec
    // rôles

    // Map<String, Object> result = new HashMap<>();
    // result.put("message", "Connexion réussie");
    // result.put("token", token);
    // result.put("user", Map.of(
    // "email", user.getEmail(),
    // "roles", user.getRoles(),
    // "prenom", user.getPrenom(),
    // "nom", user.getNom()));

    // return result;
    // }
}
