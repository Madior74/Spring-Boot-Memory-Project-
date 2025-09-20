package com.example.schooladmin.coursModule;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schooladmin.ue.UniteEnseignement;
import com.example.schooladmin.ue.UniteEnseignementRepository;

import jakarta.transaction.Transactional;

@Service
public class ModuleService {
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private UniteEnseignementRepository ueRepository;

  

    //Get All
    public List<CourseModule> getAllModules() {
        return moduleRepository.findAll();
    }

    //get by id
   public Optional<CourseModule> getModuleById(Long moduleId){
    return moduleRepository.findById(moduleId);
   }


   

    //delete
    public void deleteModule(Long id){  
        moduleRepository.deleteById(id);
    }

    //Recuperer les modules pour chaque semestre
    public List<CourseModule> getCourseModulesByUe(Long ueId){

        return moduleRepository.findByUe_Id(ueId);
    }

    //Verification de l'existence d'une UE
    public boolean moduleExist(String nomModule,Long ueId){
        return moduleRepository.existsByNomModuleAndUe_Id(nomModule, ueId);
    }





    public CourseModule saveModule(CourseModule module) {
        return moduleRepository.save(module);
    }

      public List<ModuleWithUeDTO> getAllModulesWithUe() {
        return moduleRepository.findAllWithUeInfo();
    }


    public void updateModule(Long moduleId, CourseModule moduleDetails) {
        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + moduleId));

        module.setNomModule(moduleDetails.getNomModule());
        module.setVolumeHoraire(moduleDetails.getVolumeHoraire());
        module.setCreditModule(moduleDetails.getCreditModule());
        module.setDateAjout(LocalDateTime.now());

        moduleRepository.save(module);
    }


    //
    public boolean existsByNomModuleAndUe(String nomModule,UniteEnseignement ue){
        return moduleRepository.existsByNomModuleAndUe(nomModule ,ue);
    }
}
