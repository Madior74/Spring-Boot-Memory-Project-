package com.example.schooladmin.salle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalleRepository extends JpaRepository<Salle, Long> {

       boolean existsByNomSalle(String nomSalle);

       // Vérifier si salle utilisée par une séance
       @Query("SELECT COUNT(s) > 0 FROM Seance s " +
                     "WHERE s.salle.id = :salleId " +
                     "AND s.estAnnulee = false " +
                     "AND s.dateSeance = :dateSeance " +
                     "AND ( (s.heureDebut < :heureFin AND s.heureFin > :heureDebut) )")
       boolean isSalleUsedBySeance(@Param("salleId") Long salleId,
                     @Param("dateSeance") LocalDate dateSeance,
                     @Param("heureDebut") LocalTime heureDebut,
                     @Param("heureFin") LocalTime heureFin);

       // Vérifier si salle utilisée par une évaluation
       @Query("SELECT COUNT(e) > 0 FROM Evaluation e " +
                     "WHERE e.salle.id = :salleId " +
                     "AND e.dateEvaluation = :dateEvaluation " +
                     "AND ( (e.heureDebut < :heureFin AND e.heureFin > :heureDebut) )")
       boolean isSalleUsedByEvaluation(@Param("salleId") Long salleId,
                     @Param("dateEvaluation") LocalDate dateEvaluation,
                     @Param("heureDebut") LocalTime heureDebut,
                     @Param("heureFin") LocalTime heureFin);

       @Query("SELECT sa FROM Salle sa " +
                     "WHERE NOT EXISTS (SELECT 1 FROM Seance se WHERE se.salle = sa AND se.dateSeance = :date " +
                     "  AND se.estAnnulee = false AND (se.heureDebut < :heureFin AND se.heureFin > :heureDebut)) " +
                     "  AND NOT EXISTS (SELECT 1 FROM Evaluation ev WHERE ev.salle = sa AND ev.dateEvaluation = :date "
                     +
                     "  AND (ev.heureDebut < :heureFin AND ev.heureFin > :heureDebut))")
       List<Salle> findAvailableSalles(@Param("date") LocalDate date,
                     @Param("heureDebut") LocalTime heureDebut,
                     @Param("heureFin") LocalTime heureFin);

}
