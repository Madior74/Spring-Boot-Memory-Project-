package com.example.schooladmin.dto;
import com.example.schooladmin.enums.Role;

public class AuthResponseDTO {

    private String role;
    private String email;
    private String token;

    // Constructeur prenant un Role
    public AuthResponseDTO(Role role, String email, String token) {
        this.role = role.name(); // Convertit l'énuméré en chaîne
        this.email = email;
        this.token = token;
    }

    // Getters et setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}