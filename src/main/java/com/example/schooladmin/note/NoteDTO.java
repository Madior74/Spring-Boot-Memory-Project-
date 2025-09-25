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
    private String dateEvaluation;
    private String typeEvaluation;
    private String nomEtudiant;
    private String prenomEtudiant;
    private String moduleName;
      private String nomProf;
    private String prenomProf;
    
    


     public NoteDTO(Note note) {
        this.id = note.getId();
        this.valeur = note.getValeur();
        this.etudiantId = note.getEtudiant() != null ? note.getEtudiant().getDossierAdmission().getCandidat().getId(): null;
        this.evaluationId = note.getEvaluation() != null ? note.getEvaluation().getId() : null;
        this.nomEtudiant=note.getEtudiant() != null ?note.getEtudiant().getDossierAdmission().getCandidat().getPrenom():null;
        this.moduleName=note.getEvaluation() != null && note.getEvaluation().getModule() != null ? note.getEvaluation().getModule().getNomModule() : null;
        this.nomProf=note.getEvaluation() != null && note.getEvaluation().getProfesseur() != null ? note.getEvaluation().getProfesseur().getNom() : null;
        this.prenomProf=note.getEvaluation() != null && note.getEvaluation().getProfesseur() != null ? note.getEvaluation().getProfesseur().getPrenom() : null;
        this.prenomEtudiant=note.getEtudiant() != null ?note.getEtudiant().getDossierAdmission().getCandidat().getNom():null;
        this.dateEvaluation = note.getEvaluation() != null ? note.getEvaluation().getDateEvaluation().toString() : null;
        this.typeEvaluation = note.getEvaluation() != null ? note.getEvaluation().getType() : null;
        

    }

}
