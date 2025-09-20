package com.example.schooladmin.note;

import java.time.LocalDateTime;

import com.example.schooladmin.etudiant.etudiant.Etudiant;
import com.example.schooladmin.evaluation.Evaluation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @DecimalMax(value = "20.0")
    @DecimalMin(value = "0.0")
    @Column(nullable = false)
    private Double valeur;

    @Column(nullable = false)
    private LocalDateTime dateCreation;

    private String commentaire;
    private String modifiPar;

    private String creePar;
    private LocalDateTime dateModification;

  

}