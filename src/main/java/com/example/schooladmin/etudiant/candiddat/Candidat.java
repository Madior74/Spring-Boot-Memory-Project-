package com.example.schooladmin.etudiant.candiddat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.schooladmin.anneeAcademique.AnneeAcademique;
import com.example.schooladmin.enums.Role;
import com.example.schooladmin.etudiant.admission.DossierAdmission;
import com.example.schooladmin.etudiant.document.Document;
import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.utilisateur.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "candidat")
public class Candidat extends Utilisateur {
    @Override
    public Role getRole() {
        return Role.ROLE_ETUDIANT;
    }

    @JsonIgnoreProperties({ "candidat", "documents" })
    @OneToMany(mappedBy = "candidat", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    @OneToOne(mappedBy = "candidat", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"candidat","dossierAdmission"})
    private DossierAdmission dossierAdmission;

    @JsonIgnoreProperties({"candidat","anneeAcademique"})
    @ManyToOne(optional = true)
    private AnneeAcademique anneeAcademique;

    @ManyToOne
    @JsonIgnoreProperties("dossiers")
    @JoinColumn(name = "filiere_id")
    private Filiere filiereSouhaitee;

    @ManyToOne
    @JsonIgnoreProperties("dossiers")
    @JoinColumn(name = "niveau_id")
    private Niveau niveauSouhaite;

 
   public Filiere getFiliere() {
        return dossierAdmission != null ? dossierAdmission.getFiliereAcceptee() : null;
    }

    public Niveau getNiveau() {
        return dossierAdmission != null ? dossierAdmission.getNiveauAccepte() : null;
    }


}