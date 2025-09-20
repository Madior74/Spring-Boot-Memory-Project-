package com.example.schooladmin.specialite;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface  SpecialiteRepository extends JpaRepository<Specialite,Long> {

Optional<Specialite> findByNom(String nom);
    
}
