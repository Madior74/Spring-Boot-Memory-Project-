package com.example.schooladmin.annonce;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.schooladmin.niveau.Niveau;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String message;

    private LocalDate datePublication;
    private LocalDate dateExpiration; 

    boolean annonceGenerale;

    @ManyToOne(optional = true)
    @JoinColumn(name = "niveau_id", nullable = true)
    private Niveau niveau;

    private String creePar;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    private boolean estActive;
}
