package com.example.schooladmin.assiduite;

import java.time.LocalDateTime;

import com.example.schooladmin.anneeAcademique.AnneeAcademique;
import com.example.schooladmin.etudiant.etudiant.Etudiant;
import com.example.schooladmin.seance.Seance;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "assiduite")
public class Assiduite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties({ "assiduites" })
    private Seance seance;

    @ManyToOne
    @JsonIgnoreProperties({ "assiduites" })
    private Etudiant etudiant;

    private String creerPar;
    private LocalDateTime datecreation;
    private LocalDateTime datemodification;
    private String modifierPar;

    @Enumerated(EnumType.STRING)
    private StatutPresence statutPresence = StatutPresence.PRESENT;



}
