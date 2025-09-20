package com.example.schooladmin.professeur;
import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.enums.Role;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.specialite.Specialite;
import com.example.schooladmin.utilisateur.Utilisateur;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter

public class Professeur extends Utilisateur {

    private String status;

    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(name = "professeur_specialites",
           joinColumns = @JoinColumn(name = "professeur_id"),
           inverseJoinColumns = @JoinColumn(name = "specialite_id"))
    private List<Specialite> specialites = new ArrayList<>();


    @OneToMany(mappedBy = "professeur")
    @JsonIgnore
    private List<Seance> seances = new ArrayList<>();

    @Override
    public Role getRole(){
        return Role.ROLE_PROFESSEUR;
    }

    private String inscritPar;

     

}
