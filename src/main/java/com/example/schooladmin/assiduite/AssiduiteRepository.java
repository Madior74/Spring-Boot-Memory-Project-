package com.example.schooladmin.assiduite;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.schooladmin.note.Note;
import com.example.schooladmin.seance.Seance;


@Repository
public interface  AssiduiteRepository  extends JpaRepository<Assiduite,Long> {

    List<Assiduite> findByEtudiantId(Long etuudiantId);

    boolean existsByEtudiant_IdAndSeanceIdAndStatutPresence(Long etudiant,Long seanceId,StatutPresence statutPresence);
    
         //Assiduite By Seance
     List<Assiduite> findBySeanceId(Long seanceId);



     // List<Note> findByEtudiantEmail(String email);
        List<Assiduite> findByEtudiantDossierAdmissionCandidatEmail(String email);



                // Récupérer toutes les séances d’un étudiant (via ses assiduités)
    @Query("SELECT a.seance FROM Assiduite a WHERE a.etudiant.id = :etudiantId")
    List<Seance> findSeancesByEtudiant(@Param("etudiantId") Long etudiantId);

    // Récupérer uniquement les séances où l’étudiant est ABSENT
  @Query("SELECT a.seance FROM Assiduite a WHERE a.etudiant.id = :etudiantId AND a.statutPresence = com.example.schooladmin.assiduite.StatutPresence.ABSENT")
List<Seance> findSeancesAbsentesByEtudiant(@Param("etudiantId") Long etudiantId);

}   
