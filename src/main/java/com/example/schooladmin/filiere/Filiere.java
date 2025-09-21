package com.example.schooladmin.filiere;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.schooladmin.niveau.Niveau;

import jakarta.persistence.CascadeType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;


@Entity
@Data
@Table(name = "filiere",uniqueConstraints={
    @UniqueConstraint(columnNames = {"nomFiliere"})
})
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomFiliere;
    private String description;
    private boolean actif = true;
    private String creePar;
    private Date dateCreation;
    private String modifiePar;
    private LocalDateTime dateModification;


    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Niveau> niveaux = new ArrayList<>();

    // Méthodes utilitaires (optionnelles mais utiles pour la cohérence des relations)
    public void addNiveau(Niveau niveau) {
        niveaux.add(niveau);
        niveau.setFiliere(this);
    }

    public void removeNiveau(Niveau niveau) {
        niveaux.remove(niveau);
        niveau.setFiliere(null);
    }

   
  
    // L'annotation mappedBy = "filieres" indique que Filiere est le côté inverse de la relation, 
    // et que la configuration principale est définie dans l'autre.

}
    