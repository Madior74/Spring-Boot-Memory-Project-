package com.example.schooladmin.note;

import lombok.Getter;

@Getter
public class NoteResponse {
    private Long id;
    private Double valeur;
    private Long etudiantId;
    private Long evaluationId;
    private String nomEtudiant;
    private String prenomEtudiant;
    private String emailEtudiant;

    public NoteResponse(Note note) {
        this.id = note.getId();
        this.valeur = note.getValeur();
        this.etudiantId = note.getEtudiant().getDossierAdmission().getCandidat().getId();
        this.evaluationId = note.getEvaluation() != null ? note.getEvaluation().getId() : null;
        this.prenomEtudiant = note.getEtudiant() != null
                ? note.getEtudiant().getDossierAdmission().getCandidat().getPrenom()
                : null;
        this.nomEtudiant = note.getEtudiant() != null
                ? note.getEtudiant().getDossierAdmission().getCandidat().getNom()
                : null;
 this.emailEtudiant = note.getEtudiant() != null
                ? note.getEtudiant().getDossierAdmission().getCandidat().getEmail()
                : null;

    }

}
