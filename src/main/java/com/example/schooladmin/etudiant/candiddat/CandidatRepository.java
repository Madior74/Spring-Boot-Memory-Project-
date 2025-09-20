package com.example.schooladmin.etudiant.candiddat;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Long> {

  // //Recuperer tous les etudiants d'une session
  // List<Etudiant> findByanneeAcademiqueId(@Param("anneeAcademiqueId") Long
  // anneeAcademiqueId);

  Optional<Candidat> findByCni(String cni);

  Optional<Candidat> findByIne(String ine);

  Optional<Candidat> findByEmail(String email);

  @Query("SELECT u FROM Candidat u WHERE u.email = :email")
  Optional<Candidat> findByEmailWithRoles(String email);

  boolean existsByEmail(String email);

  // Méthode pour rechercher un étudiant par son CNI ou INE
  Candidat findByCniOrIne(String cni, String ine);

  // //Recuperer les dossiers des valides des Etudiants
  // @Query("SELECT e FROM Etudiant e WHERE e.dossierAdmission.statut =
  // 'COMPLET'")
  // List<Candidat_PreInscrit> findEtudiantsAvecDossierComplet();

  // ///Etudiant disposant de 3 documents
@Query("SELECT e FROM Candidat e WHERE SIZE(e.documents) = 3")
List<Candidat> findCandidatWithThreeDocuments();
}