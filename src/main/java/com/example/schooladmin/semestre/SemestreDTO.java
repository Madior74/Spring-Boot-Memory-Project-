package com.example.schooladmin.semestre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
@AllArgsConstructor
public class SemestreDTO {

    private Long id;

    private String nomSemestre;

    private Long niveauId;

    public SemestreDTO(Semestre semestre) {
        this.id = semestre.getId();
        this.nomSemestre = semestre.getNomSemestre();
        this.niveauId = semestre.getNiveau().getId();
    }

}
