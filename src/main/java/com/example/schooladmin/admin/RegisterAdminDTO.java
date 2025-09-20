package com.example.schooladmin.admin;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterAdminDTO {
    private String nom;
    private String prenom;
    private String adresse;
    private String paysDeNaissance;
    private LocalDate dateDeNaissance;
    private String cni;
    private String ine;
    private String telephone;
    private String sexe;
    private String email;
    private String password;
    private Long regionId;
    private Long departementId;

}