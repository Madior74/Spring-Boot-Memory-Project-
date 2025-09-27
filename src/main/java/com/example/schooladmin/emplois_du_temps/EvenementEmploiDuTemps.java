package com.example.schooladmin.emplois_du_temps;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.schooladmin.seance.Seance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvenementEmploiDuTemps {
    private Long id;
    private String typeEvenement; 
    private String module;
    private String professeur;
    private String salle;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
}
