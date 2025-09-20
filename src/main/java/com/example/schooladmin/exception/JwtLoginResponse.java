package com.example.schooladmin.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtLoginResponse {
        private String accessToken;
    private String refreshToken;
    private String nom;
    private String role;
}
