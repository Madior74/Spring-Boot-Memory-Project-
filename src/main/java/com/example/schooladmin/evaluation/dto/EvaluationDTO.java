package com.example.schooladmin.evaluation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.note.NoteDTO;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class EvaluationDTO {

    private Long id;
    private String type;
    private LocalDate dateEvaluation;
    private Long moduleId;
    private Long salleId;
    private Long professeurId;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private Long anneeAcademiqueId;
    private List<NoteDTO> notes;

    public EvaluationDTO(Evaluation evaluation) {
        this.id = evaluation.getId();
        this.type = evaluation.getType();
        this.dateEvaluation = evaluation.getDateEvaluation();
        this.moduleId = evaluation.getModule() != null ? evaluation.getModule().getId() : null;
        this.salleId = evaluation.getSalle() != null ? evaluation.getSalle().getId() : null;
        this.professeurId = evaluation.getProfesseur() != null ? evaluation.getProfesseur().getId() : null;
        this.heureDebut = evaluation.getHeureDebut();
        this.heureFin = evaluation.getHeureFin();
        this.anneeAcademiqueId = evaluation.getAnneeAcademique() != null ? evaluation.getAnneeAcademique().getId() : null;

        // ⚡ Mapper les notes
        this.notes = evaluation.getNotes() != null
                ? evaluation.getNotes().stream()
                    .map(NoteDTO::new) // nécessite un constructeur NoteDTO(Note note)
                    .toList()
                : List.of();
    }

    public EvaluationDTO() {
    }



    
}
