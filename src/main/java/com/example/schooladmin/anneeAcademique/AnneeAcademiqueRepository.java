package com.example.schooladmin.anneeAcademique;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AnneeAcademiqueRepository extends JpaRepository<AnneeAcademique, Long> {

    // existence dune annee
    boolean existsByNomAnnee(String nomAnnee);

    @Modifying
    @Query("UPDATE AnneeAcademique a SET a.active = false")
    void updateAllActiveFalse();


    Optional<AnneeAcademique> findByActiveTrue();

}
