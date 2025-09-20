package com.example.schooladmin.semestre;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.ue.UniteEnseignement;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@Entity
@Data
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomSemestre;

    @ManyToOne
    @JoinColumn(name = "niveau_id", nullable = false)
    private Niveau niveau;


    @OneToMany(mappedBy = "semestre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UniteEnseignement> ues = new ArrayList<>();

   

    public String getNomFiliere() {
        if (this.niveau != null && this.niveau.getFiliere() != null) {
            return this.niveau.getFiliere().getNomFiliere();
        }
        return null; // Ou une valeur par défaut si nécessaire
    }

}
