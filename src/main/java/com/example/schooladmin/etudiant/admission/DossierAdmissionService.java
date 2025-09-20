package com.example.schooladmin.etudiant.admission;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.etudiant.candiddat.Candidat;
import com.example.schooladmin.etudiant.candiddat.CandidatRepository;


@Service
public class DossierAdmissionService {

    @Autowired
    private DossierAdmissionRepository admissionRepository;

    @Autowired
    private CandidatRepository candidatPreInscritRepository;

    //Recuperer tous les dossiers
    public List<DossierAdmission> getAllDossierAdmission() {
        return admissionRepository.findAll();
    }
    
    //Recuperer Un dossier
    public DossierAdmission getDossierAdmissionById(Long id){
        return admissionRepository.findById(id).orElseThrow(() -> new RuntimeException("DossierAdmission non Trouvé"));
    }
    

    //Recuperer les dosssiers par  Status
    public List<DossierAdmission> getDossierAdmissionByStatus(String status){
        return admissionRepository.findByStatus(status.toUpperCase());
    }

    //Recuperer les dosssiers par  Etudiant
    public Optional<DossierAdmission> getDossierAdmissionByEtudiant(Long  etudiantId){
        return admissionRepository.findByCandidat_Id(etudiantId);
    }

    //Creer un Nouveau Dossier d'Admission
    public DossierAdmission createDossier(DossierAdmission dossier, Long etudiantId) {
        Candidat etudiant = candidatPreInscritRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Etudiant non trouvé"));
        dossier.setApprouvePar(SecurityContextHolder.getContext().getAuthentication().getName());
        dossier.setDateapprouve(LocalDateTime.now());
        dossier.setCandidat(etudiant);
        return admissionRepository.save(dossier);
    }
    

//Update
public DossierAdmission updateDossier(Long id, DossierAdmission dossierDetails) {
            System.out.println("Donnee reçues:"+dossierDetails);
            System.out.println("id reçue:"+id.toString());
    DossierAdmission dossier = admissionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Dossier non trouvé"));

    dossier.setCopieCni(dossierDetails.isCopieCni());
    dossier.setNoteEntretien(dossierDetails.getNoteEntretien());
    dossier.setNoteTest(dossierDetails.getNoteTest());
    dossier.setReleveNotes(dossierDetails.isReleveNotes());
    dossier.setDiplome(dossierDetails.isDiplome());
    dossier.setStatus(dossierDetails.getStatus());
    dossier.setRemarque(dossierDetails.getRemarque());

    if (dossierDetails.getCandidat() != null) {
        Candidat etudiant = candidatPreInscritRepository.findById(dossierDetails.getCandidat().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        dossier.setCandidat(etudiant);
    }
    dossier.setMiseAjourPar(SecurityContextHolder.getContext().getAuthentication().getName());
    dossier.setDateMiseAjour(LocalDateTime.now());

    return admissionRepository.save(dossier);
}


    //Supprimer un Dossier
    public void deleteDossierAdmission(Long id){
        admissionRepository.deleteById(id);
    }


    //Verification de l'existence d'un dossier
    public  boolean existsByEtudiant(Long etudiantId){
        return  admissionRepository.existsByCandidat_Id(etudiantId);
    }
}

