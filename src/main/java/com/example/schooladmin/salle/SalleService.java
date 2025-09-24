package com.example.schooladmin.salle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
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

    public List<Salle> getAllSallesWithStatutNow() {
        List<Salle> salles = salleRepository.findAll();

        LocalDate aujourdHui = LocalDate.now();
        LocalTime maintenant = LocalTime.now();

        System.out.println("=== DIAGNOSTIC COMPLET ===");
        System.out.println("Date système Java: " + aujourdHui);
        System.out.println("Heure système Java: " + maintenant);
        System.out.println("Fuseau horaire: " + ZoneId.systemDefault());

        for (Salle salle : salles) {
            // Log détaillé pour chaque salle
            System.out.println("\n--- Salle: " + salle.getNomSalle() + " (ID: " + salle.getId() + ") ---");

            // Vérifier les séances aujourd'hui
            List<Seance> seancesAujourdhui = seanceRepository.findBySalleIdAndDateSeance(salle.getId(), aujourdHui);
            System.out.println("Nombre de séances aujourd'hui: " + seancesAujourdhui.size());

            for (Seance seance : seancesAujourdhui) {
                boolean enCours = !seance.isEstAnnulee() &&
                        maintenant.isAfter(seance.getHeureDebut()) &&
                        maintenant.isBefore(seance.getHeureFin());

                System.out.println("Séance " + seance.getId() + ": " +
                        seance.getHeureDebut() + " - " + seance.getHeureFin() +
                        " (En cours: " + enCours + ")");
            }

            boolean occupeeParSeance = seanceRepository.existsSeanceEnCours(
                    salle.getId(), aujourdHui, maintenant);

            System.out.println("Résultat existsSeanceEnCours: " + occupeeParSeance);

            salle.setOccupee(occupeeParSeance);
        }

        return salles;
    }

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

}
