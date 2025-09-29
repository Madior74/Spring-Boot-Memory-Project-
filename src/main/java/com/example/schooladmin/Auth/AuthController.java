package com.example.schooladmin.Auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;







@RestController
@RequestMapping(value = "/api/auth", produces = "application/json;charset=UTF-8")
public class AuthController {

    @Autowired
    private AuthService authService;
    



 @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("Requête reçue dans /login");
        Map<String, Object> loginResult = authService.login(loginRequest.getEmail(), loginRequest.getPassword());
        String message = (String) loginResult.getOrDefault("message", "Connexion réussie");
        String accessToken = (String) loginResult.get("accessToken");
        String refreshToken = (String) loginResult.get("refreshToken");
        String tokenType = (String) loginResult.get("tokenType");
        Map<String, Object> user = (Map<String, Object>) loginResult.get("user");
        LoginResponse response = new LoginResponse(message, user, accessToken, refreshToken, tokenType);
        return ResponseEntity.ok(response);
    }   

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            Map<String, Object> refreshResult = authService.refreshToken(request.getRefreshToken());
            
            TokenRefreshResponse response = new TokenRefreshResponse(
                (String) refreshResult.get("accessToken"),
                (String) refreshResult.get("refreshToken"),
                (Map<String, Object>) refreshResult.get("user")
            );
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequest request) {
        try {
            authService.logout(request.getRefreshToken());
            return ResponseEntity.ok().body(Map.of("message", "Déconnexion réussie"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


        public record LoginResponse(String message, Map<String, Object> user, String accessToken, String refreshToken, String tokenType) {}

}
