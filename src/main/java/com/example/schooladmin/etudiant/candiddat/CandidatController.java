package com.example.schooladmin.etudiant.candiddat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schooladmin.enums.Role;
import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.filiere.FiliereService;
import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.niveau.NiveauService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/candidat-pre-inscrit")
public class CandidatController {
    @Autowired
    private com.example.schooladmin.activity.ActivityLogService activityLogService;

    @Autowired
    private CandidatService candidatPreInscritService;

    @Autowired
    private FiliereService filiereService;

    @Autowired
    private NiveauService niveauService;

    // Ajouter un nouvel étudiant
    @PostMapping("/save")
    public Candidat ajouterEtudiant(@RequestBody Candidat etudiant) {
        System.out.println("Donnees reçues :" + etudiant);
        etudiant.setRole(Role.ROLE_ETUDIANT);
        Candidat created = candidatPreInscritService.ajouterEtudiant(etudiant);
        activityLogService.log("CANDIDAT", "Ajout d'un candidat : " + created.getNom());
        return created;
    }

    // Récupérer tous les étudiants
    @GetMapping
    public ResponseEntity<List<Candidat>> recupererTousLesEtudiants() {
        List<Candidat> etudiants = candidatPreInscritService.recupererTousLesEtudiants();
        return ResponseEntity.ok(etudiants);
    }

    // Récupérer un étudiant par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Candidat> recupererEtudiantParId(@PathVariable Long id) {
        return candidatPreInscritService.recupererEtudiantParId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer un étudiant par son ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerEtudiant(@PathVariable Long id) {
        candidatPreInscritService.supprimerEtudiant(id);
        activityLogService.log("CANDIDAT", "Suppression d'un candidat (id=" + id + ")");
        return ResponseEntity.ok("Étudiant supprimé avec succès.");
    }

    // Verification de l'existence
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkEtudiantExists(@RequestParam String email) {
        boolean exists = candidatPreInscritService.emailExists(email);
        return ResponseEntity.ok(exists);
    }

    // Récupérer les étudiants avec trois documents
    @GetMapping("/three-documents")
    public ResponseEntity<List<CandidatRequestDTO>> recupererEtudiantsAvecTroisDocuments() {
        List<Candidat> candidats = candidatPreInscritService.getEtudiantsAvecTroisDocuments();
        List<CandidatRequestDTO> dtos = candidats.stream().map(CandidatRequestDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Mettre a jour un etudiant
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEtudiant(@PathVariable("id") Long etudiantId,
            @RequestBody Map<String, Object> body) {

        // Afficher les données reçues
        System.out.println("Données reçues : " + body);

        // Vérification de l'existence de l'étudiant
        Optional<Candidat> existEtudiantOpt = candidatPreInscritService.getEtudiantById(etudiantId);
        if (!existEtudiantOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Étudiant non trouvé");
        }

        // Récupérer l'étudiant existant
        Candidat exisEtudiant = existEtudiantOpt.get();

        // Mettre à jour les propriétés de l'étudiant
        if (body.containsKey("nom")) {
            exisEtudiant.setNom((String) body.get("nom"));
        }
        if (body.containsKey("prenom")) {
            exisEtudiant.setPrenom((String) body.get("prenom"));
        }
        if (body.containsKey("adresse")) {
            exisEtudiant.setAdresse((String) body.get("adresse"));
        }
        if (body.containsKey("paysDeNaissance")) {
            exisEtudiant.setPaysDeNaissance((String) body.get("paysDeNaissance"));
        }
        if (body.containsKey("cni")) {
            exisEtudiant.setCni((String) body.get("cni"));
        }
        if (body.containsKey("ine")) {
            exisEtudiant.setIne((String) body.get("ine"));
        }
        if (body.containsKey("telephone")) {
            exisEtudiant.setTelephone((String) body.get("telephone"));
        }
        if (body.containsKey("sexe")) {
            exisEtudiant.setSexe((String) body.get("sexe"));
        }
        if (body.containsKey("email")) {
            exisEtudiant.setEmail((String) body.get("email"));
        }
        if (body.containsKey("password")) {
            exisEtudiant.setPassword((String) body.get("password"));
        }

        // Gestion de la date de naissance
        if (body.containsKey("dateDeNaissance")) {
            String dateNaissanceStr = (String) body.get("dateDeNaissance");
            try {
                exisEtudiant.setDateDeNaissance(LocalDate.parse(dateNaissanceStr));
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body("Format de date incorrect. Utilisez le format 'yyyy-MM-dd'.");
            }
        }

        // Gestion des relations (Filiere, Niveau, Session)
        if (body.containsKey("filiereSouhaitee") && body.get("filiereSouhaitee") instanceof Map) {
            Map<String, Object> filiereMap = (Map<String, Object>) body.get("filiereSouhaitee");
            Long filiereId = ((Number) filiereMap.get("id")).longValue();
            Filiere filiere = filiereService.getFiliereById(filiereId);
            if (filiere == null) {
                return ResponseEntity.badRequest().body("Filière non trouvée avec l'ID : " + filiereId);
            }
            exisEtudiant.setFiliereSouhaitee(filiere);
        }

        if (body.containsKey("niveauSouhaite") && body.get("niveauSouhaite") instanceof Map) {
            Map<String, Object> niveauMap = (Map<String, Object>) body.get("niveauSouhaite");
            Long niveauId = ((Number) niveauMap.get("id")).longValue();
            Niveau niveau = niveauService.getNiveauById(niveauId);
            if (niveau == null) {
                return ResponseEntity.badRequest().body("Niveau non trouvé avec l'ID : " + niveauId);
            }
            exisEtudiant.setNiveauSouhaite(niveau);
        }

        // Enregistrement des modifications
        try {
            Candidat updatedEtudiant = candidatPreInscritService.updateEtudiant(exisEtudiant);
            return ResponseEntity.ok(updatedEtudiant); // Retourne l'étudiant mis à jour
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour de l'étudiant : " + e.getMessage());
        }
    }

}