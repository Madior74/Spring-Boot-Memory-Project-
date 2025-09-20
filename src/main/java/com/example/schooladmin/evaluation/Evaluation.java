package com.example.schooladmin.evaluation;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.anneeAcademique.AnneeAcademique;
import com.example.schooladmin.coursModule.CourseModule;
import com.example.schooladmin.note.Note;
import com.example.schooladmin.professeur.Professeur;
import com.example.schooladmin.salle.Salle;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor  
@AllArgsConstructor
@Entity
@Table(name = "evaluation")
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // CONTROLE_CONTINU, EXAMEN, TP, ORAL...

    @Column(nullable = false)
    private LocalDate dateEvaluation;

    private LocalTime heureDebut;
    private LocalTime heureFin;

    // Module concerné
    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private CourseModule module;

        @ManyToOne
    @JoinColumn(name = "salle_id", nullable = true)
    @JsonIgnoreProperties({"seances"})
    private Salle salle;


    // Professeur responsable
    @ManyToOne
    @JoinColumn(name = "professeur_id", nullable = false)
    private Professeur professeur;

    @ManyToOne
    @JoinColumn(name = "annee_id", nullable = false)
    private AnneeAcademique anneeAcademique;

    // Notes associées
    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Note> notes = new ArrayList<>();

    // Infos d’audit
    private String creePar;
    private String modifiePar;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Méthode utilitaire : durée en minutes
    public long getDureeMinutes() {
        if (heureDebut != null && heureFin != null) {
            return Duration.between(heureDebut, heureFin).toMinutes();
        }
        return 0;
    }

    // Méthode utilitaire : durée en heures
    public long getDureeHeures() {
        return getDureeMinutes() / 60;
    }

}
