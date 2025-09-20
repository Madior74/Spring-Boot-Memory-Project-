package com.example.schooladmin.etudiant.etudiant;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;


@Data
public class EtudiantAssiduiteDTO {
    private Long id;
    private String prenom;
    private String nom;
    private String email;

    private Long filiereId;
    private Long niveauId;
    private Long anneeAcademiqueId;
    private LocalDateTime dateInscription;

    // Existing constructors, getters, setters, etc.

    public EtudiantAssiduiteDTO(Long id, String prenom, String nom, String email,Long filiereId, Long niveauId, Long anneeAcademiqueId, LocalDateTime dateInscription) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.filiereId = filiereId;
        this.niveauId = niveauId;
        this.anneeAcademiqueId = anneeAcademiqueId;
        this.dateInscription = dateInscription;
    }

    // Existing methods
}