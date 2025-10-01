package com.example.schooladmin.note;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.etudiant.etudiant.Etudiant;
import com.example.schooladmin.etudiant.etudiant.EtudiantRepository;
import com.example.schooladmin.etudiant.etudiant.EtudiantService;
import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.evaluation.EvaluationRepository;
import com.example.schooladmin.activity.ActivityLogService;
import com.example.schooladmin.coursModule.ModuleRepository;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ActivityLogService activityLogService;


    // get Note by module
    public List<Note> getNoteByEvaluationId(Long evaluationId) {
        return noteRepository.findByEvaluationId(evaluationId);
    }

    // get Note by id
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Note introuvable"));
    }

    // Note By Etudiant
    public List<Note> getNotesByEtudiantId(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    // Note By Etudiant ordered by date desc
    public List<Note> getNotesByEtudiantIdOrderByDateDesc(Long etudiantId) {
        return noteRepository.findByEtudiantIdOrderByEvaluationDateEvaluationDesc(etudiantId);
    }

    // ajouter une note
    public Note createNote(NoteCreationDTO dto) {
        Evaluation evaluation = evaluationRepository.findById(dto.getEvaluationId())
                .orElseThrow(() -> new IllegalArgumentException("Évaluation introuvable"));

        if (noteRepository.existsByEtudiantIdAndEvaluationId(dto.getEtudiantId(), dto.getEvaluationId())) {
            throw new IllegalArgumentException("Note déjà attribuée pour cette évaluation");
        }

        Etudiant etudiant = etudiantRepository.findById(dto.getEtudiantId())
                .orElseThrow(() -> new IllegalArgumentException("Étudiant introuvable"));

        Note newNote = new Note();
        newNote.setCreePar(SecurityContextHolder.getContext().getAuthentication().getName());
        newNote.setDateCreation(LocalDateTime.now());
        newNote.setEtudiant(etudiant);
        newNote.setEvaluation(evaluation);
        newNote.setValeur(dto.getValeur());

        Note saved = noteRepository.save(newNote);
        String nom = etudiant.getDossierAdmission() != null && etudiant.getDossierAdmission().getCandidat() != null
                ? etudiant.getDossierAdmission().getCandidat().getNom()
                : "";
        String prenom = etudiant.getDossierAdmission() != null && etudiant.getDossierAdmission().getCandidat() != null
                ? etudiant.getDossierAdmission().getCandidat().getPrenom()
                : "";
        activityLogService.log("NOTE", "Nouvelle note ajoutée pour " + nom + " " + prenom +
                " - Évaluation du " + evaluation.getDateEvaluation() + ": " + saved.getValeur());
        return saved;
    }

    // supprimer une note
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Note introuvable");
        }
        noteRepository.deleteById(id);
    }

    // update Note
    public Note updateNote(Long id, NoteCreationDTO dto) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note introuvable"));

        // Vérification si la mise à jour créerait un doublon
        if (noteRepository.existsByEtudiantIdAndEvaluationIdAndDateCreation(
                dto.getEtudiantId(), dto.getEvaluationId(), existingNote.getDateCreation())) {
            throw new IllegalArgumentException("Mise à jour créerait un doublon de note");
        }

        existingNote.setModifiPar(SecurityContextHolder.getContext().getAuthentication().getName());
        existingNote.setDateModification(LocalDateTime.now());
        existingNote.setEtudiant(etudiantRepository.findById(dto.getEtudiantId()).orElseThrow());
        existingNote.setEvaluation(evaluationRepository.findById(dto.getEvaluationId()).orElseThrow());
        existingNote.setValeur(dto.getValeur());

        Note saved = noteRepository.save(existingNote);
        Etudiant etu = saved.getEtudiant();
        Evaluation evaluation = saved.getEvaluation();
        String nom = etu.getDossierAdmission() != null && etu.getDossierAdmission().getCandidat() != null
                ? etu.getDossierAdmission().getCandidat().getNom()
                : "";
        String prenom = etu.getDossierAdmission() != null && etu.getDossierAdmission().getCandidat() != null
                ? etu.getDossierAdmission().getCandidat().getPrenom()
                : "";
        activityLogService.log("NOTE", "Note mise à jour pour " + nom + " " + prenom +
                " - Évaluation du " + evaluation.getDateEvaluation() + ": " + saved.getValeur());
        return saved;
    }

    // existence d'une note
    public boolean noteExists(Long etudiantId, Long evaluationId) {
        return noteRepository.existsByEtudiantIdAndEvaluationId(etudiantId, evaluationId);
    }

    public List<Note> getNotesByEtudiantEmail(String email) {
        return noteRepository.findByEtudiantDossierAdmissionCandidatEmail(email);
    }

    // Moyenne
    public Double calculMoyenne(Long moduleId) {
        List<Evaluation> evaluations = evaluationRepository.findByModuleId(moduleId);
        if (evaluations.isEmpty()) {
            throw new IllegalArgumentException("Aucune évaluation trouvée pour ce module");
        }

        Double noteExamen = null;
        double sommeAutres = 0;
        int countAutres = 0;

        for (Evaluation evalu : evaluations) {
            List<Note> notes = noteRepository.findByEvaluationId(evalu.getId());
            if (notes.isEmpty())
                continue;

            for (Note nt : notes) {
                if (nt == null || nt.getValeur() == null)
                    continue;

                if (evalu.getType().equalsIgnoreCase("EXAMEN")) {
                    // on suppose qu’il n’y a qu’une seule note examen
                    noteExamen = nt.getValeur();
                } else {
                    sommeAutres += nt.getValeur();
                    countAutres++;
                }
            }
        }

        if (noteExamen == null && countAutres == 0) {
            throw new IllegalArgumentException("Aucune note trouvée pour ce module");
        }

        double moyAutres = (countAutres > 0) ? (sommeAutres / countAutres) : 0;

        if (noteExamen != null && countAutres > 0) {
            return (noteExamen * 0.7) + (moyAutres * 0.3);
        } else if (noteExamen != null) {
            return noteExamen; // seulement examen
        } else {
            return moyAutres; // seulement autres
        }
    }

}