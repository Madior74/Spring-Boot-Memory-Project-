package com.example.schooladmin.mobile;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.schooladmin.assiduite.Assiduite;
import com.example.schooladmin.assiduite.AssiduiteRepository;
import com.example.schooladmin.assiduite.StatutPresence;
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



    public List<Seance> getSeanceByEtudiant(String email) {
        Etudiant etudiant = etudiantRepository.findByDossierAdmissionCandidatEmail(email)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec email : " + email));

        // Long etudiantId = etudiant.getDossierAdmission().getCandidat().getId();
        Long niveauId = etudiant.getNiveau().getId();

        return seanceRepository.findSeancesProgrammeesByNiveau(niveauId);
    }

    

    public List<Evaluation> getEvaluationProgrammeesByNiveauId(String email) {
        Etudiant etudiant = etudiantRepository.findByDossierAdmissionCandidatEmail(email)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec email : " + email));

        // Long etudiantId = etudiant.getDossierAdmission().getCandidat().getId();
        Long niveauId = etudiant.getNiveau().getId();
        return evaluationRepository.findEvaluationsProgrammeesByNiveau(niveauId);

    }





       // //Absences par etudiant
 public List<Seance> getAssiduiteByEtudiantEmail(String email) {
    Etudiant etudiant = etudiantRepository.findByDossierAdmissionCandidatEmail(email)
          .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec email : " + email));

    Long etudiantId = etudiant.getId(); // ✅ CORRECTION
    return seanceRepository.findSeancesAbsentesByEtudiant(etudiantId, StatutPresence.ABSENT);
}


}
