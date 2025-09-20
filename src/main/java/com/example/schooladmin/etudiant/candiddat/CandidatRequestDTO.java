package com.example.schooladmin.etudiant.candiddat;

import lombok.Data;

@Data
public class CandidatRequestDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String nomFiliere;
    private String   nomNiveau;
    private int documentCount;

    public CandidatRequestDTO(Candidat candidat){
        this.id=candidat.getId();
        this.nom=candidat.getNom();
        this.prenom=candidat.getPrenom();
        this.nomFiliere=candidat.getFiliereSouhaitee() != null ? candidat.getFiliereSouhaitee().getNomFiliere() : null;
        this.nomNiveau=candidat.getNiveauSouhaite() != null ? candidat.getNiveauSouhaite().getNomNiveau() : null;
        this.documentCount=candidat.getDocuments() != null ? candidat.getDocuments().size() : 0;
    }}