package com.example.schooladmin.salle;

// SalleOccupancyService.java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.evaluation.EvaluationRepository;
import com.example.schooladmin.evaluation.dto.EvaluationDTO;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceDTO;
import com.example.schooladmin.seance.SeanceRepository;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalleOccupancyService {

    private final SeanceRepository seanceRepository;
    private final EvaluationRepository evaluationRepository;
    private final SalleRepository salleRepository;

    public SalleOccupancyService(SeanceRepository seanceRepository,
                                 EvaluationRepository evaluationRepository,
                                 SalleRepository salleRepository) {
        this.seanceRepository = seanceRepository;
        this.evaluationRepository = evaluationRepository;
        this.salleRepository = salleRepository;
    }

    /**
     * Vérifie si la salle est disponible pour créer/modifier une séance.
     * @param salleId id de la salle
     * @param date date de la séance
     * @param heureDebut heure de début (inclus)
     * @param heureFin heure de fin (exclus recommandé)
     * @param seanceIdToExclude id de la séance en cours d'édition (null si création)
     */
    @Transactional(readOnly = true)
    public boolean isSalleAvailableForSeance(Long salleId, LocalDate date,
                                             LocalTime heureDebut, LocalTime heureFin,
                                             Long seanceIdToExclude) {
        Objects.requireNonNull(salleId, "salleId ne peut pas être null");
        boolean conflitSeance = seanceRepository.existsConflictingSeances(
                salleId, date, heureDebut, heureFin, seanceIdToExclude);
        boolean conflitEval = evaluationRepository.existsConflictingEvaluations(
                salleId, date, heureDebut, heureFin, null);
        return !(conflitSeance || conflitEval);
    }

    /**
     * Vérifie si la salle est disponible pour une évaluation.
     */
    @Transactional(readOnly = true)
    public boolean isSalleAvailableForEvaluation(Long salleId, LocalDate date,
                                                 LocalTime heureDebut, LocalTime heureFin,
                                                 Long evaluationIdToExclude) {
        Objects.requireNonNull(salleId, "salleId ne peut pas être null");
        boolean conflitEval = evaluationRepository.existsConflictingEvaluations(
                salleId, date, heureDebut, heureFin, evaluationIdToExclude);
        boolean conflitSeance = seanceRepository.existsConflictingSeances(
                salleId, date, heureDebut, heureFin, null);
        return !(conflitEval || conflitSeance);
    }

    /**
     * Retourne toutes les salles disponibles pour un créneau.
     * Requête optimisée en DB (voir SalleRepository.findAvailableSalles).
     */
    @Transactional(readOnly = true)
    public List<Salle> findAvailableSalles(LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        return salleRepository.findAvailableSalles(date, heureDebut, heureFin);
    }

    /**
     * Donne l'occupation d'une salle pour une date donnée (listes de séances / évaluations).
     */
    
@Transactional(readOnly = true)
public SalleOccupancyDTO getSalleOccupancy(Long salleId, LocalDate date) {
    Objects.requireNonNull(salleId, "salleId ne peut pas être null");
    SalleOccupancyDTO dto = new SalleOccupancyDTO();
    dto.setSalleId(salleId);
    dto.setDate(date);

    List<Seance> seances = seanceRepository.findBySalleIdAndDateSeance(salleId, date);
    List<Evaluation> evaluations = evaluationRepository.findBySalleIdAndDateEvaluation(salleId, date);

    List<SeanceDTO> seanceDTOs = (seances == null) ? Collections.emptyList()
            : seances.stream().map(this::toSeanceDTO).collect(Collectors.toList());

    List<EvaluationDTO> evaluationDTOs = (evaluations == null) ? Collections.emptyList()
            : evaluations.stream().map(this::toEvaluationDTO).collect(Collectors.toList());

    dto.setSeances(seanceDTOs);
    dto.setEvaluations(evaluationDTOs);
    return dto;
}

/* --- Mappers simples (adapte les champs selon tes DTOs) --- */
private SeanceDTO toSeanceDTO(Seance s) {
    if (s == null) return null;
    SeanceDTO d = new SeanceDTO();
    d.setId(s.getId());
    d.setDateSeance(s.getDateSeance());
    d.setHeureDebut(s.getHeureDebut());
    d.setHeureFin(s.getHeureFin());
    d.setEstEnLigne(s.isEstEnLigne());
    d.setEstAnnulee(s.isEstAnnulee());
    if (s.getSalle() != null) d.setSalleId(s.getSalle().getId());
    if (s.getModule() != null) d.setModuleId(s.getModule().getId());
    if (s.getProfesseur() != null) d.setProfesseurId(s.getProfesseur().getId());
    if (s.getAnneeAcademique() != null) d.setAnneeAcademiqueId(s.getAnneeAcademique().getId());
    d.setDateCreation(s.getDateCreation());
    return d;
}

private EvaluationDTO toEvaluationDTO(Evaluation e) {
    if (e == null) return null;
    EvaluationDTO d = new EvaluationDTO();
    d.setId(e.getId());
    d.setType(e.getType());
    d.setDateEvaluation(e.getDateEvaluation());
    d.setHeureDebut(e.getHeureDebut());
    d.setHeureFin(e.getHeureFin());
    if (e.getSalle() != null) d.setSalleId(e.getSalle().getId());
    if (e.getModule() != null) d.setModuleId(e.getModule().getId());
    if (e.getProfesseur() != null) d.setProfesseurId(e.getProfesseur().getId());
    if (e.getAnneeAcademique() != null) d.setAnneeAcademiqueId(e.getAnneeAcademique().getId());
    // si EvaluationDTO contient une liste de NoteDTO, tu peux la remplir aussi (optionnel)
    return d;
}
}

