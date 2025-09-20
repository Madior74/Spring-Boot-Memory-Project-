package com.example.schooladmin.etudiant.etudiant;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.schooladmin.etudiant.admission.DossierAdmissionDTO;

import lombok.Data;


@Data
public class EtudiantDTO {
    private Long id;
    private String prenom;
    private String nom;
    private Long filiere;
    private Long niveau;
    private Long anneeAcademique;
    private DossierAdmissionDTO dossierAdmissionDTO;
    private LocalDateTime dateInscription;
    


    public EtudiantDTO(Etudiant inscription){
        this.id=inscription.getId();
        this.prenom=inscription.getDossierAdmission().getCandidat().getPrenom();
        this.nom=inscription.getDossierAdmission().getCandidat().getNom();
        this.dossierAdmissionDTO=new DossierAdmissionDTO(inscription.getDossierAdmission());
        this.anneeAcademique=inscription.getAnneeAcademique().getId();
        this.filiere=inscription.getFiliere().getId();
        this.niveau=inscription.getNiveau().getId();
        this.dateInscription=inscription.getDateInscription();
    }
}
