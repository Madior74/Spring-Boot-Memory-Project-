package com.example.schooladmin.coursModule;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.schooladmin.ue.UniteEnseignement;



public interface ModuleRepository extends JpaRepository<CourseModule, Long> {
      @Query("SELECT new com.example.schooladmin.coursModule.ModuleWithUeDTO(m.id, m.nomModule, m.volumeHoraire, m.creditModule, m.ue.nomUE) FROM CourseModule m LEFT JOIN m.ue")
    List<ModuleWithUeDTO> findAllWithUeInfo();

    List<CourseModule> findByUe_Id(Long ueId);

    boolean existsByNomModuleAndUe_Id(String nomModule, Long ueId);


    Optional<CourseModule> findByNomModule(String nomModule);

    boolean existsByNomModuleAndUe(String nomModule, UniteEnseignement ue);


    
}