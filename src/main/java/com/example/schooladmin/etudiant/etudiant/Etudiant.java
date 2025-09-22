package com.example.schooladmin.etudiant.etudiant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.anneeAcademique.AnneeAcademique;
import com.example.schooladmin.assiduite.Assiduite;
import com.example.schooladmin.etudiant.admission.DossierAdmission;
import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.niveau.Niveau;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricule; 

    @ManyToOne(optional = false)
    private DossierAdmission dossierAdmission;

    @ManyToOne(optional = false)
    private Filiere filiere;

    @ManyToOne(optional = false)
    private Niveau niveau;

    private boolean paye; 

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assiduite> assiduites = new ArrayList<>();

    @ManyToOne(optional = false)
    private AnneeAcademique anneeAcademique;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateInscription;

    private String inscritPar;

    @Column
    private String fcmToken;
}
