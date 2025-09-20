package com.example.schooladmin.professeur;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProfesseurRepository  extends JpaRepository<Professeur, Long> {  
        Optional<Professeur> findByEmail(String email);
  
    
}
