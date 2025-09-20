package com.example.schooladmin.coursModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.ue.UniteEnseignement;
import com.example.schooladmin.ue.UniteEnseignementService;

import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/api/admin/modules")
public class ModuleController {


    @Autowired
    private ModuleService moduleService;

    @Autowired
    public ModuleRepository moduleRepository;

    @Autowired
    private UniteEnseignementService ueService;


    //recuperer tous les Modules
   
    @GetMapping
    public ResponseEntity<List<ModuleWithUeDTO>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModulesWithUe());
    }


    //Recuperer un Module  par son id
    @GetMapping("/{id}")
   public ResponseEntity<CourseModule> getModuleById(@PathVariable("id")Long id){
    Optional<CourseModule> moduleData=moduleRepository.findById(id);
    if(moduleData.isPresent()){
        return new ResponseEntity<>(moduleData.get(),HttpStatus.OK);

    }else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   }
    //get Module By UE
    @GetMapping("/ue/{ueId}")
    public ResponseEntity<Map<String, Object>> getModulesByUe(@PathVariable Long ueId) {
        Map<String, Object> response = new HashMap<>();
    
        // Récupérer les modules associés à l'UE
        List<CourseModule> modules = moduleService.getCourseModulesByUe(ueId);
    
        // Vérifier si des modules ont été trouvés
        if (modules == null || modules.isEmpty()) {
            response.put("status", "ERROR");
            response.put("message", "Aucun module trouvé pour cette UE");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    
        // Réponse en cas de succès
        response.put("status", "SUCCESS");
        response.put("message", "Modules récupérés avec succès");
        response.put("data", modules);
    
        return ResponseEntity.ok(response);
    }
    

//creer une nouveau Module
@PostMapping("/ue/{ueId}")
public ResponseEntity<Map<String, Object>> addModuleToUE(
        @PathVariable Long ueId,
        @RequestBody CourseModule courseModuleDetail) {

    Map<String, Object> response = new HashMap<>();

    // Vérification si l'UE existe
    UniteEnseignement ue = ueService.findById(ueId);
    if (ue == null) {
        response.put("status", "ERROR");
        response.put("message", "UE non trouvé");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // Vérification si le module existe déjà dans l'UE
    boolean moduleExists = moduleService.existsByNomModuleAndUe(courseModuleDetail.getNomModule(), ue);
    if (moduleExists) {
        response.put("status", "ERROR");
        response.put("message", "Ce module existe déjà dans cette UE");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // Création du module
    courseModuleDetail.setUe(ue);
    CourseModule savedCourseModule = moduleService.saveModule(courseModuleDetail);

    // Réponse en cas de succès
    response.put("status", "SUCCESS");
    response.put("message", "Module ajouté avec succès");
    response.put("data", savedCourseModule);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}


   ///Supprimer Un Module
    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
    }




    //Existence 
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkCoursModuleExist(@RequestParam String nomModule,@RequestParam Long ueId){
        boolean exists=moduleService.moduleExist(nomModule, ueId);
        return ResponseEntity.ok(exists);
    }
    



    //Volume horaire programme et déroulé
     @GetMapping("/{id}/volume-deroule")
    public ResponseEntity<Integer> getVolumeHoraireDeroule(@PathVariable Long id) {
        CourseModule module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé avec l'id : " + id));

        int volumeDeroule = module.getVolumeHoraireDeroule();

        return ResponseEntity.ok(volumeDeroule);
    }
    
}
