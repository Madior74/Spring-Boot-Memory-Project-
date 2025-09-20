package com.example.schooladmin.etudiant.admission;

import java.time.LocalDateTime;

import com.example.schooladmin.etudiant.candiddat.Candidat;
import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.niveau.Niveau;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DossierAdmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean copieCni;
    private boolean releveNotes;
    private boolean diplome;
    private String remarque;
    private double noteTest;
    private double noteEntretien;

    private String status;

    private String approuvePar;
    private LocalDateTime dateapprouve;
    private String miseAjourPar;
    private LocalDateTime dateMiseAjour;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "candidat_id", nullable = false)
    @JsonIgnoreProperties({ "dossierAdmission", "documents" })
    private Candidat candidat;

    @ManyToOne
    @JoinColumn(name = "filiere_acceptee_id", nullable = false)
    private Filiere filiereAcceptee;

    @ManyToOne
    @JoinColumn(name = "niveau_accepte_id", nullable = false)
    private Niveau niveauAccepte;


    @PreUpdate
    public void onUpdate() {
        this.dateMiseAjour = LocalDateTime.now();
    }

    @PrePersist
    public void onCreate() {
        this.dateMiseAjour = LocalDateTime.now();
    }
}