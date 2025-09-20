package com.example.schooladmin.dto;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EtudiantRequest {

    private String prenom;
    private String nom;
    private String adresse;
    private String telephone;
    private String sexe;
    private String email;
    private String role; // Assurez-vous que le rôle est inclus
    private String password;
    private LocalDate dateNaissance;
    private String imagePath;
    private String paysDeNaissance;
    private String cni;
    private String ine;

    private Long regionId;
    private Long departementId;
    private Long filiereId;
    private Long niveauId;
    private Long sessionId;

    // Getters et setters
}