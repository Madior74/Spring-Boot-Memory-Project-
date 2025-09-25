package com.example.schooladmin.evaluation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.evaluation.dto.EvaluationDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/evaluations")
@RequiredArgsConstructor
public class EvaluationController {
    @Autowired
    private com.example.schooladmin.activity.ActivityLogService activityLogService;

    private final EvaluationService evaluationService;

    private final EvaluationRepository evaluationRepository;

    // get All evaluations
    @GetMapping
    public List<Evaluation> getAllEvaluations() {
        return evaluationService.getAllEvaluations();
    }

    @GetMapping("/dto")
    public List<EvaluationDTO> getAllEvaluationsWitDTO() {
        List<Evaluation> evaluations = evaluationService.getAllEvaluations();

        return evaluations.stream().map(EvaluationDTO::new).toList();
    }

    // Evaluation By Module
    @GetMapping("/module/{moduleId}")
    public List<Evaluation> getEvaluationsByModule(@PathVariable Long moduleId) {
        System.out.println("Module ID: " + moduleId); // Debugging line
        return evaluationRepository.findByModuleId(moduleId);
    }

    // Evaluation By Module by dto
    @GetMapping("/module/{moduleId}/dto")
    public List<EvaluationDTO> getEvaluationsByModuleDTO(@PathVariable Long moduleId) {
        List<Evaluation> evaluations = evaluationRepository.findByModuleId(moduleId);
        return evaluations.stream()
                .map(EvaluationDTO::new)
                .toList();
    }

    // Ajouter une évaluation
    @PostMapping("/save")
    public ResponseEntity<Evaluation> addEvaluation(@RequestBody Evaluation evaluation) {
        Evaluation createdEvaluation = evaluationService.addEvaluation(evaluation);
        activityLogService.log("EVALUATION", "Ajout d'une évaluation (id=" + createdEvaluation.getId() + ")");
        return ResponseEntity.ok(createdEvaluation);
    }

    // Update evaluation
    @PutMapping("/update/{id}")
    public ResponseEntity<Evaluation> updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluation) {
        Evaluation updatedEvaluation = evaluationService.updateEvaluation(id, evaluation);
        if (updatedEvaluation != null) {
            activityLogService.log("EVALUATION", "Modification d'une évaluation (id=" + updatedEvaluation.getId() + ")");
            return ResponseEntity.ok(updatedEvaluation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Existence check
    @GetMapping("/exists")
    public ResponseEntity<Boolean> evaluationExists(@RequestParam Long moduleId,
            @RequestParam LocalDate dateEvaluation) {
        boolean exists = evaluationService.evaluationExists(moduleId, dateEvaluation);
        return ResponseEntity.ok(exists);
    }

    // Supprimer une évaluation
    @DeleteMapping("/delete/{id}")
    public void deleteEvaluation(@PathVariable Long id) {
        evaluationService.deleteEvaluation(id);
        activityLogService.log("EVALUATION", "Suppression d'une évaluation (id=" + id + ")");
    }

}
