package com.example.schooladmin.filiere;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {

    Optional<Filiere> findByNomFiliere(String nomFiliere);

    Optional<Filiere> findByNomFiliereIgnoreCase(String nomFiliere);

    boolean existsByNomFiliere(String nomFiliere);

}
