package com.example.schooladmin.annonce;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnnonceRequest {
    private String titre;
    private String message;
    private LocalDate datePublication;
    private LocalDate dateExpiration;
    private Boolean annonceGenerale; // if true, niveauId can be null
    private Long niveauId; // optional when annonceGenerale is true
    private Boolean estActive; // optional, defaults to true if null
} 