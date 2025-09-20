package com.example.schooladmin.ue;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schooladmin.semestre.Semestre;


@Repository
public interface UniteEnseignementRepository extends JpaRepository<UniteEnseignement, Long> {

    List<UniteEnseignement> findBySemestreId(Long semestreId);
    List<UniteEnseignement> findByNomUE(String nomUE);
    boolean existsByNomUEAndSemestre(String nomUE, Semestre semestre);
    boolean existsByNomUEAndSemestreId(String nomUE,Long semestreId);

    
    
}
