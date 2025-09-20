package com.example.schooladmin.note;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoteDTO {
    
    private Long id;
    private Double valeur;
    private Long etudiantId;
    private Long evaluationId;
    private String nomEtudiant;
    private String prenomEtudiant;
    


     public NoteDTO(Note note) {
        this.id = note.getId();
        this.valeur = note.getValeur();
        this.etudiantId = note.getEtudiant() != null ? note.getEtudiant().getDossierAdmission().getCandidat().getId(): null;
        this.evaluationId = note.getEvaluation() != null ? note.getEvaluation().getId() : null;
        this.nomEtudiant=note.getEtudiant() != null ?note.getEtudiant().getDossierAdmission().getCandidat().getPrenom():null;
        this.prenomEtudiant=note.getEtudiant() != null ?note.getEtudiant().getDossierAdmission().getCandidat().getNom():null;

    }

}
