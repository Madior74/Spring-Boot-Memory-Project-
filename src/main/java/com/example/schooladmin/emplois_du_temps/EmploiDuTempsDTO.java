package com.example.schooladmin.emplois_du_temps;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.schooladmin.seance.Seance;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmploiDuTempsDTO {

    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String moduleNom;
    private String professeurNom;
    private String professeurPreNom;
    private String salleNom;
    private String typeSeance; // Optionnel : si tu veux différencier CM, TD, TP

    // Constructeur, getters, setters...
    public EmploiDuTempsDTO(Seance seance) {
        this.date = seance.getDateSeance();
        this.heureDebut = seance.getHeureDebut();
        this.heureFin = seance.getHeureFin();
        this.moduleNom = seance.getModule().getNomModule();
        this.professeurNom = seance.getProfesseur().getNom(); 
        this.professeurPreNom = seance.getProfesseur().getPrenom(); 
        this.salleNom = seance.getSalle() != null ? seance.getSalle().getNomSalle() : "En ligne";
        this.typeSeance = seance.isEstEnLigne() ? "En ligne" : "Présentiel";
    }

    // Getters & Setters
}