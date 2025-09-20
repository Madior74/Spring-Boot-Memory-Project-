package com.example.schooladmin.professeur;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.enums.Role;
import com.example.schooladmin.specialite.Specialite;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/admin/professeurs")
public class ProfesseurController {

    @Autowired
    private ProfesseurService professeurService;

    // Lister tous les professeurs
    @GetMapping
    public List<Professeur> getAllProfesseurs() {
        return professeurService.getAllProfesseurs();
    }

    // Obtenir un professeur par ID
    @GetMapping("/{id}")
    public Optional<Professeur> getProfesseurById(@PathVariable Long id) {
        return professeurService.getProfesseurById(id);
    }

    // Créer un nouveau professeur
    @PostMapping("/save")
    public Professeur createProfesseur(@RequestBody Professeur professeur) {
        System.out.println("Donnees du prof reçuess:" + professeur);
        professeur.setRole(Role.ROLE_PROFESSEUR);
        return professeurService.createProfesseur(professeur);
    }

    // Mettre à jour un professeur
    @PutMapping("/update/{id}")
    public ResponseEntity<Professeur> updateProfesseur(
            @PathVariable Long id,
            @RequestBody Professeur professeurDetails) {
        System.out.println("Donnees reçues" + professeurDetails);
        if(professeurService.getProfesseurById(id).isPresent()){
            professeurDetails.setId(id);
                    professeurDetails.setRole(Role.ROLE_PROFESSEUR);

                    return ResponseEntity.ok(professeurService.createProfesseur(professeurDetails));


        }

        return ResponseEntity.notFound().build();
    }

    // Supprimer un professeur
    @DeleteMapping("/{id}")
    public void deleteProfesseur(@PathVariable Long id) {
        professeurService.deleteProfesseur(id);
    }

    // Specialité d'un professeur
    @GetMapping("/{id}/specialites")
    public List<Specialite> getSpecialitesByProfesseurId(@PathVariable Long id) {
        return professeurService.getSpecialitesByProfesseurId(id);
    }

    // Ajouter une spécialité à un professeur
    @PostMapping("/{id}/specialites")
    public void addSpecialitesToProfesseur(
            @PathVariable Long id,
            @RequestBody List<Long> specialiteIds) {
        professeurService.addSpecialitesToProfesseur(id, specialiteIds);
    }

    // Supprimer une spécialité d'un professeur
    @DeleteMapping("/{id}/specialites/{specialiteId}")
    public void removeSpecialiteFromProfesseur(@PathVariable Long id, @PathVariable Long specialiteId) {
        professeurService.removeSpecialiteFromProfesseur(id, specialiteId);
    }

    // Obtenir le nombre de professeurs
    @GetMapping("/count")
    public long countProfesseurs() {
        return professeurService.countProfesseurs();
    }
}