package com.example.schooladmin.emplois_du_temps;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.evaluation.EvaluationRepository;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceRepository;

@Service
public class EmploiDuTempsService {

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    public List<EvenementEmploiDuTemps> genererEmploiDuTemps(Long niveauId, LocalDate debut, LocalDate fin) {
        List<EvenementEmploiDuTemps> result = new ArrayList<>();

        // Charger les séances
        List<Seance> seances = seanceRepository.findByNiveauAndDateBetween(niveauId, debut, fin);
        for (Seance s : seances) {
            result.add(new EvenementEmploiDuTemps(
                s.getId(),
                "SEANCE",
                s.getModule().getNomModule(),
                s.getProfesseur().getNom(),
                s.getSalle() != null ? s.getSalle().getNomSalle() : (s.isEstEnLigne() ? "En ligne" : "Non attribuée"),
                s.getDateSeance(),
                s.getHeureDebut(),
                s.getHeureFin()
            ));
        }

        // Charger les évaluations
        List<Evaluation> evaluations = evaluationRepository.findByNiveauAndDateBetween(niveauId, debut, fin);
        for (Evaluation e : evaluations) {
            result.add(new EvenementEmploiDuTemps(
                e.getId(),
                "EVALUATION",
                e.getModule().getNomModule() + " (" + e.getType() + ")",
                e.getProfesseur().getNom(),
                e.getSalle() != null ? e.getSalle().getNomSalle() : "Non attribuée",
                e.getDateEvaluation(),
                e.getHeureDebut(),
                e.getHeureFin()
            ));
        }

        // Trier par date et heure
        result.sort(Comparator.comparing(EvenementEmploiDuTemps::getDate)
                .thenComparing(EvenementEmploiDuTemps::getHeureDebut));

        return result;
    }
}
