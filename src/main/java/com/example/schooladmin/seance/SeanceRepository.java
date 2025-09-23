package com.example.schooladmin.seance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.schooladmin.salle.Salle;

public interface SeanceRepository extends JpaRepository<Seance, Long> {
        List<Seance> findByModuleId(Long moduleId);

        List<Seance> findByProfesseurId(Long professeurId);

        // Exister une séance à une date et heure donnée
        boolean existsByModuleIdAndDateSeanceAndHeureDebutAndHeureFin(Long moduleId, LocalDate dateSeance,
                        LocalTime heureDebut, LocalTime heureFin);

        @Query("SELECT s FROM Salle s WHERE s.id NOT IN (" +
                        "SELECT se.salle.id FROM Seance se " +
                        "WHERE se.dateSeance = :date " +
                        "AND (se.heureDebut < :heureFin AND se.heureFin > :heureDebut) " +
                        "AND se.estAnnulee = false " +
                        "UNION " +
                        "SELECT ev.salle.id FROM Evaluation ev " +
                        "WHERE ev.dateEvaluation = :date " +
                        "AND (ev.heureDebut < :heureFin AND ev.heureFin > :heureDebut))")
        List<Salle> findSallesDisponibles(@Param("date") LocalDate date,
                        @Param("heureDebut") LocalTime heureDebut,
                        @Param("heureFin") LocalTime heureFin);

        // boolean existsBySalleAndDateSeanceAndHeures(Long salleId, LocalDate date,
        // LocalTime heure);

        @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
                        "FROM Seance s " +
                        "WHERE s.salle.id = :salleId " +
                        "  AND s.dateSeance = :date " +
                        "  AND s.estAnnulee = false " +
                        "  AND (s.heureDebut < :heureFin AND s.heureFin > :heureDebut) " +
                        "  AND (:seanceIdToExclude IS NULL OR s.id <> :seanceIdToExclude)")
        boolean existsConflictingSeances(@Param("salleId") Long salleId,
                        @Param("date") LocalDate date,
                        @Param("heureDebut") LocalTime heureDebut,
                        @Param("heureFin") LocalTime heureFin,
                        @Param("seanceIdToExclude") Long seanceIdToExclude);

        @Query("SELECT s FROM Seance s " +
                        "WHERE s.salle.id = :salleId " +
                        "  AND s.dateSeance = :date " +
                        "  AND s.estAnnulee = false " +
                        "  AND (s.heureDebut < :heureFin AND s.heureFin > :heureDebut) " +
                        "  AND (:seanceIdToExclude IS NULL OR s.id <> :seanceIdToExclude)")
        List<Seance> findConflictingSeances(@Param("salleId") Long salleId,
                        @Param("date") LocalDate date,
                        @Param("heureDebut") LocalTime heureDebut,
                        @Param("heureFin") LocalTime heureFin,
                        @Param("seanceIdToExclude") Long seanceIdToExclude);

        List<Seance> findBySalleIdAndDateSeance(Long salleId, LocalDate date);

        // Filtrer par semaine ou période

        @Query("SELECT s FROM Seance s " +
                        "JOIN s.module m " +
                        "JOIN m.ue ue " +
                        "JOIN ue.semestre sem " +
                        "WHERE sem.niveau.id = :niveauId " +
                        "AND s.dateSeance BETWEEN :startDate AND :endDate " +
                        "ORDER BY s.dateSeance, s.heureDebut")
        List<Seance> findSeancesByNiveauIdAndDateRange(
                        @Param("niveauId") Long niveauId,
                        @Param("startDate") LocalDate startDate,
                        @Param("endDate") LocalDate endDate);




    // Récupérer toutes les séances programmées pour un niveau
  @Query("SELECT s FROM Seance s " +
       "WHERE s.module.ue.semestre.niveau.id = :niveauId " +
       "AND s.dateSeance > CURRENT_DATE")
List<Seance> findSeancesProgrammeesByNiveau(@Param("niveauId") Long niveauId);

}
