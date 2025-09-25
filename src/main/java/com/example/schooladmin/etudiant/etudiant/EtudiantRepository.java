package com.example.schooladmin.etudiant.etudiant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.statistique.FiliereTendanceDTO;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    // //Verification si l'etudiant est deja inscrit dans le Systeme
    // boolean existsByEtudiantId(Long etudiantId);

    boolean existsByDossierAdmission_Candidat_IdAndFiliere_Id(
            Long etudiantId,
            Long filiereId);

    @Query("SELECT COUNT(i) FROM Etudiant i WHERE i.filiere.id = :filiereId")
    int countEtudiantsByFiliereId(@Param("filiereId") Long filiereId);

    @Query("SELECT COUNT(i) FROM Etudiant i WHERE i.niveau.id = :niveauId")
    int countEtudiantsByNiveauId(@Param("niveauId") Long niveauId);

    // Par niveau
    @Query("SELECT i FROM Etudiant i WHERE i.niveau.id = :niveauId")
    List<Etudiant> findByNiveauId(@Param("niveauId") Long niveauId);

@Query("SELECT e FROM Evaluation e " +
       "WHERE e.dateEvaluation > CURRENT_DATE")
List<Evaluation> findEvaluationsProgrammees();


    Optional<Etudiant> findByDossierAdmissionCandidatEmail(String email);


    //Statistiques
     @Query("SELECT e.filiere.nom AS filiere, COUNT(e) AS total " +
           "FROM Etudiant e " +
           "WHERE e.anneeAcademique.id = :anneeId " +
           "GROUP BY e.filiere.nom")
    List<FiliereTendanceDTO> getTendanceParFiliere(@Param("anneeId") Long anneeId);




}
