package com.example.schooladmin.etudiant.etudiant;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.anneeAcademique.AnneeAcademiqueRepository;
import com.example.schooladmin.etudiant.admission.DossierAdmission;
import com.example.schooladmin.etudiant.admission.DossierAdmissionRepository;
import com.example.schooladmin.etudiant.candiddat.Candidat;
import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.filiere.FiliereRepository;
import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.niveau.NiveauRepository;
import com.example.schooladmin.activity.ActivityLogService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantService {

    private final FiliereRepository filiereRepository;
    private final NiveauRepository niveauRepository;
    private final DossierAdmissionRepository admissionRepository;
    private final EtudiantRepository inscriptionRepository;
    private final EtudiantRepository etudiantRepository;
    private final ActivityLogService activityLogService;

    // recuperer tous les inscriptions with dto

    // Nouvelle Inscription
    public Etudiant ajouterInscription(Etudiant inscription) {
        Long dossierId = inscription.getDossierAdmission().getId();
        DossierAdmission dossier = admissionRepository.findById(dossierId)
                .orElseThrow(() -> new RuntimeException("impossible de trouver le dossier de l'Etudiant"));

        // Verification de la validation du dossier
        if (inscription.getDossierAdmission() == null
                // || !"valide".equals(inscription.getDossierAdmission().getStatus())
                
                ) {
            throw new RuntimeException("Inscription impossiblle dossier non Validé");
        }

        // Chargement des objets liés (sécurité contre objets partiels)
        Filiere filiere = filiereRepository.findById(inscription.getFiliere().getId())
                .orElseThrow(() -> new RuntimeException("Filière introuvable"));
        Niveau niveau = niveauRepository.findById(inscription.getNiveau().getId())
                .orElseThrow(() -> new RuntimeException("Niveau introuvable"));

        inscription.setDossierAdmission(dossier);
        inscription.setFiliere(filiere);
        inscription.setNiveau(niveau);
        inscription.setInscritPar(SecurityContextHolder.getContext().getAuthentication().getName());
        inscription.setDateInscription(LocalDateTime.now());

        Etudiant saved = inscriptionRepository.save(inscription);

        String nom = dossier.getCandidat() != null ? dossier.getCandidat().getNom() : "";
        String prenom = dossier.getCandidat() != null ? dossier.getCandidat().getPrenom() : "";
        activityLogService.log("ETUDIANT", "Nouvel étudiant inscrit : " + nom + " " + prenom);

        return saved;
    }

   

    // Existence de l'inscription
    public boolean checkIfInscriptionExists(Long etudiantId, Long filiereId) {
        return inscriptionRepository.existsByDossierAdmission_Candidat_IdAndFiliere_Id(etudiantId, filiereId);
    }

    // Par niveau
    public List<Etudiant> getInscriptionsByNiveauId(Long niveauId) {
        return inscriptionRepository.findByNiveauId(niveauId);
    }

    
    
    public Etudiant creerEtudiantDepuisDossier(DossierAdmission dossier) {
        if (!dossier.getStatus().equals("ACCEPTE")) {
            throw new IllegalStateException("Impossible de créer un étudiant : dossier non validé");
        }

        Candidat candidat = dossier.getCandidat();
       
        Etudiant etudiant = new Etudiant();
        etudiant.setDossierAdmission(dossier);
        etudiant.setDateInscription(LocalDateTime.now());
        etudiant.setInscritPar(dossier.getApprouvePar());

        Etudiant saved = etudiantRepository.save(etudiant);
        String nom = candidat != null ? candidat.getNom() : "";
        String prenom = candidat != null ? candidat.getPrenom() : "";
        activityLogService.log("ETUDIANT", "Nouvel étudiant inscrit : " + nom + " " + prenom);
        return saved;
    }

    

   
}
