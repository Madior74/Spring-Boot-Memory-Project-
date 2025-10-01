package com.example.schooladmin.evaluation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.anneeAcademique.AnneeAcademique;
import com.example.schooladmin.anneeAcademique.AnneeAcademiqueService;
import com.example.schooladmin.salle.SalleOccupancyService;
import com.example.schooladmin.salle.SalleOccupiedException;
import com.example.schooladmin.salle.SalleRepository;
import com.example.schooladmin.activity.ActivityLogService;

@Service
public class EvaluationService {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private AnneeAcademiqueService anneeAcademiqueService;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private SalleOccupancyService salleOccupancyService;

    @Autowired
    private ActivityLogService activityLogService;

    // Ajouter une évaluation
    public Evaluation addEvaluation(Evaluation evaluation) {
        // Verification de l'existence dune anne active
        AnneeAcademique activatedAnnee = anneeAcademiqueService.getActiveAnneeAcademique();
        if (activatedAnnee == null) {
            throw new RuntimeException(
                    "Aucune année académique active. Veuillez activer une année avant de créer une séance.");
        }

       
        if (!salleOccupancyService.isSalleAvailableForEvaluation(
                evaluation.getSalle().getId(),
                evaluation.getDateEvaluation(),
                evaluation.getHeureDebut(),
                evaluation.getHeureFin(),
                evaluation.getId() // null si création
        )) {
            throw new SalleOccupiedException("La salle est déjà occupée pour ce créneau.");
        }

        evaluation.setCreePar(SecurityContextHolder.getContext().getAuthentication().getName());
        evaluation.setDateCreation(LocalDateTime.now());
        // Attribuer l'anne active
        evaluation.setAnneeAcademique(anneeAcademiqueService.getActiveAnneeAcademique());

        Evaluation saved = evaluationRepository.save(evaluation);
        activityLogService.log("EVALUATION", "Nouvelle évaluation ajoutée pour le " + saved.getDateEvaluation());
        return saved;
    }

    // Get All evaluations
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    // Get evaluation by id
    public Evaluation getEvaluationById(Long id) {
        return evaluationRepository.findById(id).orElse(null);
    }

    // Delete evaluation by id
    public void deleteEvaluation(Long id) {
        evaluationRepository.deleteById(id);
    }

    // Update evaluation
    public Evaluation updateEvaluation(Long id, Evaluation evaluation) {
        Evaluation existingEvaluation = evaluationRepository.findById(id).orElse(null);
        if (existingEvaluation != null) {
            existingEvaluation.setType(evaluation.getType());
            existingEvaluation.setDateEvaluation(evaluation.getDateEvaluation());
            existingEvaluation.setHeureDebut(evaluation.getHeureDebut());
            existingEvaluation.setHeureFin(evaluation.getHeureFin());
            existingEvaluation.setModule(evaluation.getModule());
            existingEvaluation.setProfesseur(evaluation.getProfesseur());
            existingEvaluation.setDateModification(LocalDateTime.now());
            existingEvaluation.setModifiePar(SecurityContextHolder.getContext().getAuthentication().getName());
            Evaluation saved = evaluationRepository.save(existingEvaluation);
            activityLogService.log("EVALUATION", "Évaluation mise à jour pour le " + saved.getDateEvaluation());
            return saved;
        }

        return null;
    }

    // Existence check
    public boolean evaluationExists(Long moduleId, LocalDate dateEvaluation) {
        return evaluationRepository.existsByModuleIdAndDateEvaluation(moduleId, dateEvaluation);
    }


     //Evaluations par niveau
    public List<Evaluation> getEvaluationsByNiveau(Long niveauId) {
        return evaluationRepository.findEvaluationsByNiveau(niveauId);
    }

}
