package com.example.schooladmin.anneeAcademique;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.seance.Seance;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnneeAcademique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomAnnee;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDebut;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFin;

    @OneToMany(mappedBy = "anneeAcademique", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Seance> seances = new ArrayList<>();

        private boolean active;
        private String creePar;
        private String modifiePar;
        private LocalDate dateCreation;
        private LocalDate dateModification;
}
