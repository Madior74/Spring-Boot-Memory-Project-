package com.example.schooladmin.assiduite;

import java.time.LocalDateTime;

import com.example.schooladmin.etudiant.etudiant.Etudiant;
import com.example.schooladmin.etudiant.etudiant.EtudiantAssiduiteDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AssiduiteDTO {
    private Long id;
    private Long seanceId;
    private EtudiantAssiduiteDTO etudiantDTO;
    private StatutPresence statutPresence;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private String creerPar;
    private String modifierPar;


    public AssiduiteDTO(Assiduite assiduite){
        this.id=assiduite.getId();
        this.seanceId=assiduite.getSeance().getId();
        this.statutPresence=assiduite.getStatutPresence();
        this.dateCreation=assiduite.getDatecreation();
        this.creerPar=assiduite.getCreerPar();
        this.dateModification=assiduite.getDatemodification();
        this.modifierPar=assiduite.getModifierPar();
        Etudiant etudiant= assiduite.getEtudiant();
        if(etudiant != null){
            this.etudiantDTO=new EtudiantAssiduiteDTO(
                etudiant.getId(),
                etudiant.getDossierAdmission().getCandidat().getPrenom(),
                etudiant.getDossierAdmission().getCandidat().getNom(),
                etudiant.getDossierAdmission().getCandidat().getEmail(),
                etudiant.getFiliere().getId(),
                etudiant.getNiveau().getId(),
                etudiant.getAnneeAcademique().getId(),
                etudiant.getDateInscription()
            );

        }
    }}
