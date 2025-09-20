package com.example.schooladmin.Auth;

import java.util.Map;

public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Map<String, Object> user;
    
    public TokenRefreshResponse() {}
    
    public TokenRefreshResponse(String accessToken, String refreshToken, Map<String, Object> user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public String getTokenType() {
        return tokenType;
    }
    
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public Map<String, Object> getUser() {
        return user;
    }
    
    public void setUser(Map<String, Object> user) {
        this.user = user;
    }
} 