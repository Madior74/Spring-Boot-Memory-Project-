package com.example.schooladmin.etudiant.admission;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/admin/dossiers")
public class DossierAdmissionController {

    @Autowired
    private DossierAdmissionService dossierAdmissionService;

    @Autowired
    private DossierAdmissionRepository admissionRepository;





    // Dossier par Id
    @GetMapping("/{id}")
    public DossierAdmission getDossierAdmissionById(@PathVariable Long id) {
        return dossierAdmissionService.getDossierAdmissionById(id);
    }

    // tous les Dossier
    @GetMapping()
    public ResponseEntity<List<DossierAdmissionDTO>> getAllDossier() {
        List<DossierAdmission> dossier = admissionRepository.findAll();
        List<DossierAdmissionDTO> dtos = dossier.stream().map(DossierAdmissionDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    //Dossier par Etudiant
    @GetMapping("/etudiant/{id}")
    public DossierAdmission getDossierAdmissionByEtudiantId(@PathVariable Long id){
        return dossierAdmissionService.getDossierAdmissionByEtudiant(id).orElseThrow(() -> new RuntimeException("Dossier Non Trouvé"));
    }

    //Dossier par etudiant avec dto
    // @GetMapping("/etudiant/{id}")
    // public DossierAdmissionDTO getDossierAdmissionByEtudiantId(@PathVariable Long id){
    //     DossierAdmission dossier= dossierAdmissionService.getDossierAdmissionByEtudiant(id).orElseThrow(() -> new RuntimeException("Dossier Non Trouvé"));

    //     DossierAdmissionDTO dto = new DossierAdmissionDTO(dossier);
    //     return dto;
    // }


    // Creer un nouveau dossier
    @PostMapping("/save")
    public DossierAdmission createDossier(@RequestBody DossierAdmission dossier) {
        System.out.println("Donnees reçues :" + dossier);
        System.out.println("ID du candidat reçu : " + dossier.getCandidat().getId());
        return dossierAdmissionService.createDossier(dossier, dossier.getCandidat().getId());
        
    }
    // public ResponseEntity<?> createDossierAdmission(@RequestBody DossierAdmissionDTO dto) {
    //     System.err.println("ID du candidat reçu : " + dto.getCandidat().getId());

    //     // Vérifie s'il existe
    //     boolean existe = candidatPreInscritRepository.existsById(dto.getCandidat().getId());
    //     System.err.println("Candidat existe en base ? " + existe);

    //     if (!existe) {
    //         return ResponseEntity.badRequest()
    //                 .body("Candidat non trouvé avec l'ID : " + dto.getCandidat().getId());
    //     }

    //     try {
    //         // Créer l'objet Gestion_Admission à partir du DTO
    //         DossierAdmission dossierAdmission = new DossierAdmission();
    //         dossierAdmission.setCopieCni(dto.isCopieCni());
            
    //         dossierAdmission.setReleveNotes(dto.isReleveNotes());
    //         dossierAdmission.setDiplome(dto.isDiplome());
    //         dossierAdmission.setStatutVoeu(dto.getStatutVoeu());
    //         dossierAdmission.setRemarque(dto.getRemarque());
    //         dossierAdmission.setNoteTest(dto.getNoteTest());
    //         dossierAdmission.setNoteEntretien(dto.getNoteEntretien());

    //         DossierAdmission saved = dossierAdmissionService.createDossier(dossierAdmission, dto.getCandidat().getId());
    //         return ResponseEntity.ok(saved);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.status(500).body("Erreur serveur : " + e.getMessage());
    //     }
    // }

    // Update
    @PutMapping("/update/{id}")
    public ResponseEntity<DossierAdmission> updateDossier(@PathVariable Long id,
            @RequestBody DossierAdmission dossierDetails) {
        DossierAdmission updated = dossierAdmissionService.updateDossier( id,dossierDetails);
        return ResponseEntity.ok(updated);
    }

    // Existence
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkDossierExists(@RequestParam Long etudiantId) {
        boolean exists = dossierAdmissionService.existsByEtudiant(etudiantId);
        return ResponseEntity.ok(exists);
    }

    // supprime d’abord la référence dans l'étudiant, ensuite supprime le dossier
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deleteDossier(@PathVariable Long id) {
        Optional<DossierAdmission> dossierOptional = admissionRepository.findById(id);

        if (!dossierOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dossier non trouvé");
        }

        DossierAdmission dossier = dossierOptional.get();

        if (dossier.getCandidat() != null) {
            dossier.getCandidat().setDossierAdmission(null);
        }

        admissionRepository.delete(dossier);

        return ResponseEntity.ok("Dossier supprimé");
    }

}
