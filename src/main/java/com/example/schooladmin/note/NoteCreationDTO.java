package com.example.schooladmin.note;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NoteCreationDTO {
    
    private Long etudiantId;
    private Long evaluationId;
    private Double valeur;
    private Long professeurId;


    public NoteCreationDTO(Note note) {
        this.etudiantId = note.getEtudiant().getId();
        this.evaluationId = note.getEvaluation().getId();
        this.valeur = note.getValeur();
    
    }

}
