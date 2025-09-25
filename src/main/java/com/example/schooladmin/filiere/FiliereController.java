package com.example.schooladmin.filiere;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.schooladmin.niveau.Niveau;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/admin/filieres")
public class FiliereController {
    @Autowired
    private com.example.schooladmin.activity.ActivityLogService activityLogService;
    @Autowired
    private FiliereService filiereService;

    @Autowired
    private FiliereRepository filiereRepository;

    @GetMapping("/{id}/niveaux")
    public List<Niveau> getNiveauxByFiliere(@PathVariable("id") Long id) {
        return filiereService.getNiveauxByFiliere(id);
    }

    // Afficher la liste des filieres
    @GetMapping
    public List<Filiere> getAllFilieres() {
        return filiereService.getAllFilieres();
    }

    // Auto
    @PostMapping("/auto")
    public ResponseEntity<Filiere> createFiliere(@RequestBody FiliereRequest request) {
        Filiere filiere = filiereService.createFiliereAvecStructure(request);
        activityLogService.log("FILIERE", "Ajout d'une filière : " + filiere.getNomFiliere());
        return ResponseEntity.ok(filiere);
    }

    @DeleteMapping("/deleteFiliere/{id}")
    public void deleteFiliere(@PathVariable("id") Long id) {
        filiereService.deleteFiliere(id);
        activityLogService.log("FILIERE", "Suppression d'une filière (id=" + id + ")");
    }

    // nombre d'etudiant    
    @GetMapping("/{id}/etudiants/count")
    public ResponseEntity<Map<String, Integer>> getEtudiantsCount(@PathVariable Long id) {
        int count = filiereService.getEtudiantsCountByFiliereId(id);
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }

    // Mettre a jour
    @PutMapping("/{id}")
    public Filiere updateFiliere(@PathVariable Long id, Filiere updatedFiliere) {
        Filiere filiere = filiereRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filière non trouvée"));

        filiere.setNomFiliere(updatedFiliere.getNomFiliere());
        filiere.setDescription(updatedFiliere.getDescription());
        Filiere saved = filiereRepository.save(filiere);
        activityLogService.log("FILIERE", "Modification d'une filière : " + saved.getNomFiliere());
        return saved;
    }

    @GetMapping("/voir/{id}")
    public Filiere getFiliereById(@PathVariable("id") Long id) {
        return filiereService.getFiliereById(id);
    }

    // Existence
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkFiliereExist(@RequestParam String nomFiliere) {
        boolean exists = filiereService.existsByNomFiliere(nomFiliere);
        return ResponseEntity.ok(exists);
    }

}