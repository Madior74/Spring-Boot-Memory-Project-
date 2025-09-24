package com.example.schooladmin.salle;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SalleStatutDTO {
    private Long id;
    private String nomSalle;
    private String equipements;
    private boolean occupeeMaintenant;      // Séance en cours en ce moment
    private boolean avaitSeanceAujourdhui;  // A eu une séance aujourd'hui (même terminée)
    private String statut;                  // Statut textuel
    
    // Méthode utilitaire pour déterminer le statut
    public String getStatut() {
        if (occupeeMaintenant) {
            return "OCCUPEE";
        } else if (avaitSeanceAujourdhui) {
            return "DEJA_UTILISEE";
        } else {
            return "LIBRE";
        }
    }
}