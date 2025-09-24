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
import com.example.schooladmin.note.Note;
import com.example.schooladmin.note.NoteRepository;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceRepository;
import com.example.schooladmin.semestre.Semestre;
import com.example.schooladmin.semestre.SemestreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EtudiantDashboardService {

    private final AssiduiteRepository assiduiteRepository;
    private final SeanceRepository seanceRepository;
    private final EvaluationRepository evaluationRepository;
    private final EtudiantRepository etudiantRepository;
    private final SemestreRepository semestreRepository;
    private final NoteRepository noteRepository;

    public List<Seance> getSeanceByEtudiant(String email) {
        Etudiant etudiant = etudiantRepository.findByDossierAdmissionCandidatEmail(email)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec email : " + email));

        // Long etudiantId = etudiant.getDossierAdmission().getCandidat().getId();
        Long niveauId = etudiant.getNiveau().getId();

        return seanceRepository.findSeancesProgrammeesByNiveau(niveauId);
    }

    // Evaluations
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

        Long etudiantId = etudiant.getId();
        return seanceRepository.findSeancesAbsentesByEtudiant(etudiantId, StatutPresence.ABSENT);
    }

    // getSemestresByEtudiant

public List<Semestre> getSemestresByEtudiant(String email) {
    Etudiant etudiant = etudiantRepository.findByDossierAdmissionCandidatEmail(email)
          .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec email : " + email));
            Long niveauId = etudiant.getNiveau().getId();

    return semestreRepository.findByNiveauId(niveauId);
}


//Note de l'etudiant
     public List<Note> getNotesByEtudiantEmail(String email) {
        return noteRepository.findByEtudiantDossierAdmissionCandidatEmail(email);
    }
}