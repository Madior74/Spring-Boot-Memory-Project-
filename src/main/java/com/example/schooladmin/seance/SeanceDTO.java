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
    private String nomSalle;
    private Long moduleId;
    private String nomModule;
    private String nomProf;
    private String prenomProf;
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
            this.nomSalle = seance.getSalle() != null ? seance.getSalle().getNomSalle() : null;
        }
        this.moduleId = seance.getModule() != null ? seance.getModule().getId() : null;
        this.nomModule = seance.getModule() != null ? seance.getModule().getNomModule() : null;

        this.professeurId = seance.getProfesseur() != null ? seance.getProfesseur().getId() : null;
        this.nomProf = seance.getProfesseur() != null ? seance.getProfesseur().getNom() : null;
        this.prenomProf = seance.getProfesseur() != null ? seance.getProfesseur().getPrenom() : null;
        this.anneeAcademiqueId = seance.getAnneeAcademique() != null ? seance.getAnneeAcademique().getId() : null;
    }
  
}