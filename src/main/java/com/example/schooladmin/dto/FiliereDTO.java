package com.example.schooladmin.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiliereDTO {
    private Long id;
    private String nomFiliere;

    public FiliereDTO(Long id, String nomFiliere) {
        this.id = id;
        this.nomFiliere = nomFiliere;
    }

    // Getters and Setters
}