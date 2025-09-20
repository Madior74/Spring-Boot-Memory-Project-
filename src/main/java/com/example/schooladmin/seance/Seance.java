package com.example.schooladmin.seance;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.anneeAcademique.AnneeAcademique;
import com.example.schooladmin.assiduite.Assiduite;
import com.example.schooladmin.coursModule.CourseModule;
import com.example.schooladmin.professeur.Professeur;
import com.example.schooladmin.salle.Salle;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class Seance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateSeance;


    private LocalTime heureDebut; 
    private LocalTime heureFin;
    private boolean estEnLigne;
    private boolean estAnnulee;

        private String creePar;
        private LocalDateTime dateModification;
        private LocalDateTime dateCreation;

        

    
    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = true)
    @JsonIgnoreProperties({"seances"})
    private Salle salle;


    @ManyToOne
    @JsonIgnoreProperties({"seances"})
    private CourseModule module;

    @ManyToOne
    @JsonIgnoreProperties({"seances"})
    private Professeur professeur;

    @ManyToOne
    @JsonIgnoreProperties({"seances"})
    private AnneeAcademique anneeAcademique;

    @OneToMany(mappedBy = "seance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assiduite> assiduites = new ArrayList<>();




    // Durée en heures
    public int getDureeEnHeures() {
        return (int) Duration.between(heureDebut, heureFin).toHours();
    }




    public boolean isDeroulee() {
        if (this.estAnnulee) {
            return false;
        }
        if (this.dateSeance == null || this.heureFin == null) {
            return false;
        }

        LocalDateTime finSeance = LocalDateTime.of(this.dateSeance, this.heureFin);
        return finSeance.isBefore(LocalDateTime.now());
    }

    public boolean isProgrammee() {
        return !this.estAnnulee &&
                this.dateSeance != null &&
                this.dateSeance.isAfter(LocalDate.now());
    }

    public boolean isEnCours() {
        if (this.estAnnulee || this.dateSeance == null ||
                this.heureDebut == null || this.heureFin == null) {
            return false;
        }

        LocalDateTime maintenant = LocalDateTime.now();
        LocalDateTime debut = LocalDateTime.of(this.dateSeance, this.heureDebut);
        LocalDateTime fin = LocalDateTime.of(this.dateSeance, this.heureFin);

        return maintenant.isAfter(debut) && maintenant.isBefore(fin);
    }

    
}
