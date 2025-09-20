
package com.example.schooladmin.professeur;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.schooladmin.enums.Role;
import com.example.schooladmin.region.Region;
import com.example.schooladmin.region.departement.Departement;
import com.example.schooladmin.specialite.Specialite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfesseurDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    private String paysDeNaissance;
    private LocalDate dateDeNaissance;
    private String imagePath;
    private String cni;
    private String ine;
    private String telephone;
    private String sexe;
    private String email;
    private Role role;
    private String status;
    private List<Specialite> specialites;
    private Region region;
    private Departement departement;
    private LocalDateTime dateAjout;

    // Constructeur depuis l'entité
    public ProfesseurDTO(Professeur professeur) {
        this.id = professeur.getId();
        this.nom = professeur.getNom();
        this.prenom = professeur.getPrenom();
        this.adresse = professeur.getAdresse();
        this.paysDeNaissance = professeur.getPaysDeNaissance();
        this.dateDeNaissance = professeur.getDateDeNaissance();
        this.imagePath = professeur.getImagePath();
        this.cni = professeur.getCni();
        this.ine = professeur.getIne();
        this.telephone = professeur.getTelephone();
        this.sexe = professeur.getSexe();
        this.email = professeur.getEmail();
        this.role = professeur.getRole();
        this.status = professeur.getStatus();
        this.specialites = professeur.getSpecialites();
        this.region = professeur.getRegion();
        this.departement = professeur.getDepartement();
        this.dateAjout = professeur.getDateAjout();
    }

}