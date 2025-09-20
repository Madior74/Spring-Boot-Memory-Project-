package com.example.schooladmin.etudiant.document;

import java.time.LocalDateTime;

import com.example.schooladmin.etudiant.candiddat.Candidat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // Diplôme, Relevé de Notes, Certification, etc.
    private String type; // PDF, Image, etc.

    private String cheminFichier; // Chemin du fichier stocké

    private LocalDateTime dateDepot; // Date de dépôt du document

    // @JsonIgnoreProperties({"documents","candidat_pre_inscrit"})
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "candidat_id")
    private Candidat  candidat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public LocalDateTime getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDateTime dateDepot) {
        this.dateDepot = dateDepot;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

  
 
}