package com.example.schooladmin.professeur;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.specialite.Specialite;
import com.example.schooladmin.specialite.SpecialiteRepository;
import com.example.schooladmin.activity.ActivityLogService;


@Service
public class ProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private ActivityLogService activityLogService;
    

    // Récupérer tous les professeurs
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    // Récupérer par ID
    public Optional<Professeur> getProfesseurById(Long id) {
        return professeurRepository.findById(id);
    }

    // Créer un nouveau professeur
    public Professeur createProfesseur(Professeur professeur) {

        professeur.setInscritPar(SecurityContextHolder.getContext().getAuthentication().getName());
        professeur.setDateAjout(LocalDateTime.now());
        Professeur saved = (professeurRepository.save(professeur));
        activityLogService.log("PROFESSEUR", "Nouveau professeur ajouté : " + saved.getNom() + " " + saved.getPrenom());
        return saved;
    }

    // Mettre à jour un professeur
     public Professeur updateProfesseur(Professeur professeur) {

        professeur.setModierPar(SecurityContextHolder.getContext().getAuthentication().getName());
        professeur.setDateModifie(LocalDateTime.now());
        return (professeurRepository.save(professeur));
    }

    // Supprimer un professeur
    public void deleteProfesseur(Long id) {
        professeurRepository.deleteById(id);
    }



    //Specialité par professeur
    public List<Specialite> getSpecialitesByProfesseurId(Long id) {
        Professeur professeur = professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec l'id " + id));
        return professeur.getSpecialites();
}   


    // Ajouter une spécialité à un professeur
    public void addSpecialitesToProfesseur(Long profId, List<Long> specialiteIds) {
        Professeur professeur = professeurRepository.findById(profId)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));

        List<Specialite> specialites = specialiteRepository.findAllById(specialiteIds);

        professeur.setSpecialites(specialites);    
        professeurRepository.save(professeur);
    }
    // Supprimer une spécialité d'un professeur
    public void removeSpecialiteFromProfesseur(Long id, Long specialiteId) {
        Professeur professeur = professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec l'id " + id));
        Specialite specialite = professeur.getSpecialites().stream()
                .filter(s -> s.getId().equals(specialiteId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Spécialité non trouvée avec l'id " + specialiteId));
        professeur.getSpecialites().remove(specialite);
        professeurRepository.save(professeur);
    }
    // Obtenir le nombre de professeurs
    public long countProfesseurs() {
        return professeurRepository.count();
    }
}