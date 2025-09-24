package com.example.schooladmin.evaluation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

        List<Evaluation> findByModuleId(Long moduleId);

        boolean existsByModuleIdAndDateEvaluation(Long moduleId, LocalDate dateEvaluation);

        // boolean existsBySalleAndDateEvaluationAndHeures(Long salleId, LocalDate date,
        // LocalTime heure);

        @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
                        "FROM Evaluation e " +
                        "WHERE e.salle.id = :salleId " +
                        "  AND e.dateEvaluation = :date " +
                        "  AND (e.heureDebut < :heureFin AND e.heureFin > :heureDebut) " +
                        "  AND (:evaluationIdToExclude IS NULL OR e.id <> :evaluationIdToExclude)")
        boolean existsConflictingEvaluations(@Param("salleId") Long salleId,
                        @Param("date") LocalDate date,
                        @Param("heureDebut") LocalTime heureDebut,
                        @Param("heureFin") LocalTime heureFin,
                        @Param("evaluationIdToExclude") Long evaluationIdToExclude);

        @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END " +
                        "FROM Evaluation e " +
                        "WHERE e.salle.id = :salleId " +
                        "  AND e.dateEvaluation = :date " +
                        "  AND e.heureDebut < :heureCourante " +
                        "  AND e.heureFin > :heureCourante " +
                        "  AND (:evaluationIdToExclude IS NULL OR e.id <> :evaluationIdToExclude)")
        boolean existsConflictingEvaluationsNow(@Param("salleId") Long salleId,
                        @Param("date") LocalDate date,
                        @Param("heureCourante") LocalTime heureCourante,
                        @Param("evaluationIdToExclude") Long evaluationIdToExclude);

        @Query("SELECT e FROM Evaluation e " +
                        "WHERE e.salle.id = :salleId " +
                        "  AND e.dateEvaluation = :date " +
                        "  AND (e.heureDebut < :heureFin AND e.heureFin > :heureDebut) " +
                        "  AND (:evaluationIdToExclude IS NULL OR e.id <> :evaluationIdToExclude)")
        List<Evaluation> findConflictingEvaluations(@Param("salleId") Long salleId,
                        @Param("date") LocalDate date,
                        @Param("heureDebut") LocalTime heureDebut,
                        @Param("heureFin") LocalTime heureFin,
                        @Param("evaluationIdToExclude") Long evaluationIdToExclude);

        List<Evaluation> findBySalleIdAndDateEvaluation(Long salleId, LocalDate date);

        @Query("SELECT e FROM Evaluation e " +
                        "WHERE e.module.ue.semestre.niveau.id = :niveauId " +

                        "AND e.dateEvaluation >= CURRENT_DATE")
        List<Evaluation> findEvaluationsProgrammeesByNiveau(@Param("niveauId") Long niveauId);

}
