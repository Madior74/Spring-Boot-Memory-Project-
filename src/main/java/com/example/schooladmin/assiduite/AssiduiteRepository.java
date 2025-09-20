package com.example.schooladmin.assiduite;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  AssiduiteRepository  extends JpaRepository<Assiduite,Long> {

    List<Assiduite> findByEtudiantId(Long etuudiantId);

    boolean existsByEtudiant_IdAndSeanceIdAndStatutPresence(Long etudiant,Long seanceId,StatutPresence statutPresence);
    
         //Assiduite By Seance
     List<Assiduite> findBySeanceId(Long seanceId);

}   
