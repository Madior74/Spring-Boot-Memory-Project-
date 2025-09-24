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
import com.example.schooladmin.professeur.ProfesseurRepository;

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

   
  

    // get Note by module
    public List<Note> getNoteByEvaluationId(Long evaluationId) {
        return noteRepository.findByEvaluationId(evaluationId);
    }

    //get Note by id
    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Note introuvable"));
    }

    //Note By Etudiant
    public List<Note> getNotesByEtudiantId(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId);
    }

    //Note By Etudiant ordered by date desc
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

    return noteRepository.save(newNote);
}

    // supprimer une note
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Note introuvable");
        }
        noteRepository.deleteById(id);
    }

    //update Note
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

        return noteRepository.save(existingNote);
    }

    //existence d'une note
    public boolean noteExists(Long etudiantId,Long evaluationId) {
        return noteRepository.existsByEtudiantIdAndEvaluationId(etudiantId,  evaluationId);
    }


    // public MoyenneModuleDTO calculerMoyenneModule(String moduleCode) {
    // List<Note> notesModule =
    // noteRepository.findByEvaluationModuleCode(moduleCode);

    // if (notesModule.isEmpty()) {
    // return new MoyenneModuleDTO(moduleCode, 0.0, 0);
    // }

    // double sommeNotes = 0.0;
    // double sommeCoefficients = 0.0;

    // for (Note note : notesModule) {
    // double coefficient = note.getEvaluation().getCoefficient();
    // sommeNotes += note.getValeur() * coefficient;
    // sommeCoefficients += coefficient;
    // }

    // double moyenne = sommeCoefficients > 0 ? sommeNotes / sommeCoefficients :
    // 0.0;

    // return new MoyenneModuleDTO(moduleCode, moyenne, notesModule.size());
    // }

      public List<Note> getNotesByEtudiantEmail(String email) {
        return noteRepository.findByEtudiantDossierAdmissionCandidatEmail(email);
    }

}