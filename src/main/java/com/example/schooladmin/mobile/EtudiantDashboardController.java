package com.example.schooladmin.mobile;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/etudiants/dashboard")
@RequiredArgsConstructor
public class EtudiantDashboardController {

    private final EtudiantDashboardService dashboardService;

    @GetMapping
    public DashboardEtudiantDTO getDashboard(Authentication authentication) {
        // Email de l’étudiant connecté via Spring Security
        String email = authentication.getName();
        return dashboardService.getDashboardByEmail(email);
    }
}
