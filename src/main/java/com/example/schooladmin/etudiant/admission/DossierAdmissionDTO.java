package com.example.schooladmin.etudiant.admission;

import com.example.schooladmin.etudiant.candiddat.CandidatDTO;
import com.example.schooladmin.etudiant.candiddat.Candidat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DossierAdmissionDTO {

    private Long id;
    private boolean copieCni;
    private boolean releveNotes;
    private boolean diplome;
    private String remarque;
    private double noteTest;
    private double noteEntretien;
    private String niveauSouhaite;
    private Long niveauSouhaiteId;
    private String filiereAcceptee;
    private Long filiereAccepteeId;
    private String status;
    private String filiereSouhaitee;
    private Long filiereSouhaiteeId;
    private String niveauAccepte;
    private Long niveauAccepteId;
    private CandidatDTO candidat;

    public DossierAdmissionDTO(DossierAdmission dossier) {
        this.id = dossier.getId();
        this.copieCni = dossier.isCopieCni();
        this.releveNotes = dossier.isReleveNotes();
        this.diplome = dossier.isDiplome();
        this.remarque = dossier.getRemarque();
        this.noteTest = dossier.getNoteTest();
        this.noteEntretien = dossier.getNoteEntretien();
        this.status = dossier.getStatus();

        this.filiereSouhaitee = dossier.getCandidat().getFiliereSouhaitee() != null
                ? dossier.getCandidat().getFiliereSouhaitee().getNomFiliere()
                : "Non spécifié";
        this.filiereSouhaiteeId = dossier.getCandidat().getFiliereSouhaitee().getId();
        this.niveauSouhaiteId = dossier.getCandidat().getNiveauSouhaite().getId();
        this.filiereAccepteeId = dossier.getFiliereAcceptee().getId();
        this.niveauAccepteId = dossier.getNiveauAccepte().getId();
        this.niveauSouhaite = dossier.getCandidat().getNiveauSouhaite() != null
                ? dossier.getCandidat().getNiveauSouhaite().getNomNiveau()
                : "Non spécifié";

        this.filiereAcceptee = dossier.getFiliereAcceptee() != null ? dossier.getFiliereAcceptee().getNomFiliere()
                : "Non spécifié";
        this.niveauAccepte = dossier.getNiveauAccepte() != null ? dossier.getNiveauAccepte().getNomNiveau()
                : "Non spécifié";
        Candidat candidat = dossier.getCandidat();
        if (candidat != null) {
            this.candidat = new CandidatDTO(candidat.getId(), candidat.getNom(), candidat.getPrenom(),
                    candidat.getEmail(),
                    candidat.getAnneeAcademique().getId(),
                    candidat.getFiliereSouhaitee().getId(), candidat.getNiveauSouhaite().getId());

        }
    }

}
