package com.example.schooladmin.seance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SeanceRepository extends JpaRepository<Seance, Long> {
    List<Seance> findByModuleId(Long moduleId);
    List<Seance> findByProfesseurId(Long professeurId);

    //Exister une séance à une date et heure donnée
    boolean existsByModuleIdAndDateSeanceAndHeureDebutAndHeureFin(Long moduleId, LocalDate dateSeance, LocalTime heureDebut, LocalTime heureFin);
}
