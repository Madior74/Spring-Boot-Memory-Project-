package com.example.schooladmin.etudiant.admission;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierAdmissionRepository extends JpaRepository<DossierAdmission, Long> {


    boolean existsByCandidat_Id(Long etudiantId);

    Optional<DossierAdmission> findByCandidat_Id(Long etudiantId);

    //findByStatus
    List<DossierAdmission> findByStatus(String status);

}
