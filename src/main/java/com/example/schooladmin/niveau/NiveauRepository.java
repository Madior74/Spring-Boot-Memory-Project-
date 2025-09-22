package com.example.schooladmin.niveau;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.seance.Seance;


@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {
    Optional<Niveau> findByNomNiveauAndFiliere(String nomNiveau, Filiere filiere);
    List<Niveau> findByFiliereId(Long filiereId);

    //Verification
    boolean existsByNomNiveauAndFiliereId(String nomNiveau, Long filiereId);  
    boolean existsByNomNiveauAndFiliere(String nomNiveau, Filiere filiere);


     @Query("SELECT s FROM Seance s " +
           "JOIN s.module m " +
           "JOIN m.ue ue " +
           "JOIN ue.semestre sem " +
           "WHERE sem.niveau.id = :niveauId " +
           "ORDER BY s.dateSeance, s.heureDebut")
    List<Seance> findSeancesByNiveauId(@Param("niveauId") Long niveauId);

    
}

