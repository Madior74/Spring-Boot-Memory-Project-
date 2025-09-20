package com.example.schooladmin.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NiveauDTO {
    private Long id;
    private String nomNiveau;
    private FiliereDTO filiere;

}