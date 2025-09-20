package com.example.schooladmin.seance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor

@Getter
@Setter
public class SeanceDTO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateSeance;

    private LocalTime heureDebut;
    private LocalTime heureFin;  

    private boolean estEnLigne;
    private boolean estAnnulee;

    private Long salleId;
    private Long moduleId;
    private Long professeurId;
    private Long anneeAcademiqueId;
    private LocalDateTime dateCreation;



   
    public SeanceDTO(Seance seance) {
        this.id = seance.getId();
        this.dateSeance = seance.getDateSeance();
        this.heureDebut = seance.getHeureDebut();
        this.heureFin = seance.getHeureFin();
        this.estEnLigne = seance.isEstEnLigne();
        this.estAnnulee = seance.isEstAnnulee();

        this.dateCreation=seance.getDateCreation();
        if (!seance.isEstEnLigne()) {
            this.salleId = seance.getSalle() != null ? seance.getSalle().getId() : null;
        }
        this.moduleId = seance.getModule() != null ? seance.getModule().getId() : null;
        this.professeurId = seance.getProfesseur() != null ? seance.getProfesseur().getId() : null;
        this.anneeAcademiqueId = seance.getAnneeAcademique() != null ? seance.getAnneeAcademique().getId() : null;
    }
  
}