package com.example.schooladmin.niveau;
import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.semestre.Semestre;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Niveau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomNiveau;


    @JsonIgnore  // Empêche la sérialisation infinie
    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;

    @JsonIgnore
    @OneToMany(mappedBy = "niveau", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Semestre> semestres = new ArrayList<>();

    // Constructeurs
    public Niveau() {}

    public Niveau(String nomNiveau, Filiere filiere) {
        this.nomNiveau = nomNiveau;
        this.filiere = filiere;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomNiveau() { return nomNiveau; }
    public void setNomNiveau(String nomNiveau) { this.nomNiveau = nomNiveau; }

    public Filiere getFiliere() { return filiere; }
    public void setFiliere(Filiere filiere) { this.filiere = filiere; }

    public List<Semestre> getSemestres() { return semestres; }
    public void setSemestres(List<Semestre> semestres) { this.semestres = semestres; }



    @Override
    public String toString() {
        return nomNiveau; // Retourne le nom du niveau
    }
}
