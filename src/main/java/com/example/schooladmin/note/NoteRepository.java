package com.example.schooladmin.note;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEtudiantId(Long etudiantId);
    List<Note> findByEvaluationId(Long evaluationId);

    //findByEtudiantIdOrderByEvaluationDateEvaluationDesc
    List<Note> findByEtudiantIdOrderByEvaluationDateEvaluationDesc(Long etudiantId);

    boolean existsByEtudiantIdAndEvaluationId(Long etudiantId,Long evaluationId);
    // List<Note> findByEtudiantEmail(String email);
        List<Note> findByEtudiantDossierAdmissionCandidatEmail(String email);

    boolean existsByEtudiantIdAndEvaluationIdAndDateCreation(Long etudiantId,Long evaluationId,LocalDateTime dateCreation);
}
