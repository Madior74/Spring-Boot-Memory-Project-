package com.example.schooladmin.salle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schooladmin.evaluation.EvaluationRepository;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceRepository;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private SeanceRepository seanceRepository;

        // Définir le fuseau horaire cible (Europe/Paris)
    private static final ZoneId TARGET_ZONE = ZoneId.of("Europe/Paris");


    // get
    public List<Salle> getAllSalles() {
        return salleRepository.findAll();
    }

    // public List<Salle> getAllSalles() {
    // List<Salle> salles = salleRepository.findAll();

    // LocalDate aujourdHui = LocalDate.now();
    // LocalTime maintenant = LocalTime.now();

    // for (Salle salle : salles) {
    // boolean occupeeParSeance =
    // seanceRepository.existsBySalleAndDateSeanceAndHeures(
    // salle.getId(), aujourdHui, maintenant);

    // boolean occupeeParEval =
    // evaluationRepository.existsBySalleAndDateEvaluationAndHeures(
    // salle.getId(), aujourdHui, maintenant);

    // salle.setOccupee(occupeeParSeance || occupeeParEval);
    // }

    // return salles;
    // }

    // Salles with statut
    // public List<Salle> getAllSallesWithStatut(LocalDate date, LocalTime
    // heureDebut, LocalTime heureFin) {
    // List<Salle> salles = salleRepository.findAll();

    // for (Salle salle : salles) {
    // boolean occupeeParSeance = seanceRepository.existsConflictingSeances(
    // salle.getId(), date, heureDebut, heureFin, null);

    // boolean occupeeParEval = evaluationRepository.existsConflictingEvaluations(
    // salle.getId(), date, heureDebut, heureFin, null);

    // salle.setOccupee(occupeeParSeance || occupeeParEval);
    // }

    // return salles;
    // }

    // Create
    public Salle createSalle(Salle salle) {
        return salleRepository.save(salle);

    }

    // delete
    public void deleteSalle(Long id) {
        salleRepository.deleteById(id);
    }

    // Existence
    public boolean existsByNomSalle(String nomSalle) {
        return salleRepository.existsByNomSalle(nomSalle);
    }

    // Vérifier si salle utilisée par une séance ou évaluation
    public boolean isSalleUsed(Long salleId, LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        boolean usedBySeance = salleRepository.isSalleUsedBySeance(salleId, date, heureDebut, heureFin);
        boolean usedByEvaluation = salleRepository.isSalleUsedByEvaluation(salleId, date, heureDebut, heureFin);
        return usedBySeance || usedByEvaluation;
    }



      // Méthode existante (gardée pour compatibilité)
    public List<Salle> getAllSallesWithStatutNow() {
        List<Salle> salles = salleRepository.findAll();
        LocalDate aujourdHui = LocalDate.now();
        LocalTime maintenant = LocalTime.now();

        for (Salle salle : salles) {
            boolean occupeeParSeance = seanceRepository.existsSeanceEnCours(
                    salle.getId(), aujourdHui, maintenant);

            boolean occupeeParEval = evaluationRepository.existsConflictingEvaluationsNow(
                    salle.getId(), aujourdHui, maintenant, null);

            salle.setOccupee(occupeeParSeance || occupeeParEval);
        }
        return salles;
    }

    public List<SalleStatutDTO> getAllSallesWithStatutDetaille() {
        List<Salle> salles = salleRepository.findAll();
        
        // Utiliser l'heure européenne plutôt que l'heure du serveur
        ZonedDateTime nowInEurope = ZonedDateTime.now(TARGET_ZONE);
        LocalDate aujourdHui = nowInEurope.toLocalDate();
        LocalTime maintenant = nowInEurope.toLocalTime();
        
        System.out.println("=== DEBUG TIMEZONE ===");
        System.out.println("Heure serveur (Oregon): " + LocalDateTime.now());
        System.out.println("Heure Europe: " + maintenant);
        System.out.println("Date Europe: " + aujourdHui);
        
        List<SalleStatutDTO> resultat = new ArrayList<>();
        
        for (Salle salle : salles) {
            boolean occupeeMaintenant = seanceRepository.existsSeanceEnCours(
                    salle.getId(), aujourdHui, maintenant);


                        boolean avaitSeanceAujourdhui = seanceRepository.existsSeanceAujourdhui(
                    salle.getId(), aujourdHui);
            
            // Vérifier les évaluations en cours
            boolean occupeeParEval = evaluationRepository.existsConflictingEvaluationsNow(
                    salle.getId(), aujourdHui, maintenant, null);
            
            // Si occupée par une évaluation, cela prime
            if (occupeeParEval) {
                occupeeMaintenant = true;
            }
            
            SalleStatutDTO dto = new SalleStatutDTO(
                    salle.getId(),
                    salle.getNomSalle(),
                    salle.getEquipements(),
                    occupeeMaintenant,
                    avaitSeanceAujourdhui,
                    null // Le statut sera calculé automatiquement
            );
            resultat.add(dto);
            
        }
        
        return resultat;
    }

    



}
