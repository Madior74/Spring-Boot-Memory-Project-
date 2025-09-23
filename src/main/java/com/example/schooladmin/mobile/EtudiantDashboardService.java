package com.example.schooladmin.mobile;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.schooladmin.assiduite.AssiduiteRepository;
import com.example.schooladmin.etudiant.etudiant.Etudiant;
import com.example.schooladmin.etudiant.etudiant.EtudiantRepository;
import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.evaluation.EvaluationRepository;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EtudiantDashboardService {

    private final AssiduiteRepository assiduiteRepository;
    private final SeanceRepository seanceRepository;
    private final EvaluationRepository evaluationRepository;
    private final EtudiantRepository etudiantRepository;

    public DashboardEtudiantDTO getDashboardByEmail(String email) {
        // Étape 1 : récupérer l’étudiant à partir de son email
        Etudiant etudiant = etudiantRepository.findByDossierAdmissionCandidatEmail(email)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec email : " + email));

        Long etudiantId = etudiant.getId();
        Long niveauId = etudiant.getNiveau().getId();

        // Étape 2 : récupérer les données
        List<Seance> mesSeances = assiduiteRepository.findSeancesByEtudiant(etudiantId);
        List<Seance> mesAbsences = assiduiteRepository.findSeancesAbsentesByEtudiant(etudiantId);
        List<Seance> seancesProgrammeesNiveau = seanceRepository.findSeancesProgrammeesByNiveau(niveauId);
        List<Evaluation> evaluationsProgrammees = evaluationRepository.findEvaluationsProgrammees();

        // Étape 3 : retourner un DTO
        return new DashboardEtudiantDTO(
                mesSeances,
                mesAbsences,
                seancesProgrammeesNiveau,
                evaluationsProgrammees
        );
    }
}
