package com.example.schooladmin.anneeAcademique;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.Data;

@Data
@Service
public class AnneeAcademiqueService {
    @Autowired
    private AnneeAcademiqueRepository anneeAcademiqueRepository;

    public Optional<AnneeAcademique> getAnneeAcademiqueById(final Long id) {
        return anneeAcademiqueRepository.findById(id);

    }

    // Get All
    public List<AnneeAcademique> getAllAnneeAcademique() {
        return anneeAcademiqueRepository.findAll();
    }

    // Create and update Methode
    public AnneeAcademique creategetAnneeAcademique(AnneeAcademique anneeAcademique) {
        anneeAcademique.setCreePar(SecurityContextHolder.getContext().getAuthentication().getName());
        anneeAcademique.setDateCreation(LocalDate.now());
        return anneeAcademiqueRepository.save(anneeAcademique);
    }

    // delete
    public void deletegetAnneeAcademique(final Long id) {
        anneeAcademiqueRepository.deleteById(id);
    }

    // Exiatence dune annee
    public boolean existByNomAnnee(String nomAnnee) {
        return anneeAcademiqueRepository.existsByNomAnnee(nomAnnee);
    }


    // Activer une année académique
    @Transactional
    public void activerAnnee(Long id) {
        // désactive tout en 1 seul UPDATE
        anneeAcademiqueRepository.updateAllActiveFalse();

        // active la nouvelle
        AnneeAcademique annee = anneeAcademiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Année introuvable"));
        annee.setActive(true);
        anneeAcademiqueRepository.save(annee);
    }


      public AnneeAcademique getActiveAnneeAcademique() {
        return anneeAcademiqueRepository.findByActiveTrue()
            .orElseThrow(() -> new IllegalStateException("⚠️ Aucune année académique active"));
    }

}
