package com.example.schooladmin.etudiant.etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EtudiantRepository  extends JpaRepository<Etudiant,Long>{
// //Verification si l'etudiant est deja inscrit dans le Systeme
// boolean existsByEtudiantId(Long etudiantId);

boolean existsByDossierAdmission_Candidat_IdAndFiliere_Id(
    Long etudiantId,
    Long filiereId
);


 @Query("SELECT COUNT(i) FROM Etudiant i WHERE i.filiere.id = :filiereId")
 int countEtudiantsByFiliereId(@Param("filiereId") Long filiereId);

  @Query("SELECT COUNT(i) FROM Etudiant i WHERE i.niveau.id = :niveauId")
 int countEtudiantsByNiveauId(@Param("niveauId") Long niveauId);



 //Par niveau
 @Query("SELECT i FROM Etudiant i WHERE i.niveau.id = :niveauId")
List<Etudiant> findByNiveauId(@Param("niveauId") Long niveauId);


// List<Etudiant>findByEtudiantId(Long etudiantId);
}
