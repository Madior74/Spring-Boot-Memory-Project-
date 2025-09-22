package com.example.schooladmin.seance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schooladmin.coursModule.CourseModule;
import com.example.schooladmin.coursModule.ModuleRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/seances")
public class SeanceController {

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private  SeanceRepository seanceRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @GetMapping
    public List<Seance> getAllSeances() {
        return seanceService.getAllSeances();
    }

    @GetMapping("/{id}")
    public Optional<Seance> getSeanceById(@PathVariable Long id) {
        return seanceService.getSeanceById(id);
    }

   
    
    @PostMapping("/save")
    // public ResponseEntity<?> createSeance(@RequestBody SeanceDTO dto) {
    // // 1. Créer la séance via le service
    // Seance createdSeance = seanceService.createSeance(dto);

    // // 2. Récupérer le module associé
    // CourseModule module = createdSeance.getModule();

    // // 4. Construire la réponse
    // Map<String, Object> response = new HashMap<>();
    // response.put("seance", createdSeance);
    // response.put("module_id", module.getId());

    // return ResponseEntity.ok(response);
    // }
public ResponseEntity<Seance> createSeance(@RequestBody SeanceDTO dto) {
    Seance createdSeance = seanceService.createSeance(dto);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdSeance);
}


   

    // Mettre à jour une séance
    @PutMapping("/{id}")
    public Seance updateSeance(@PathVariable Long id, @RequestBody SeanceDTO dto) {
        return seanceService.updateSeance(id, dto);
    }

    //Supprimer une séance
    @DeleteMapping("/{id}")
    public void deleteSeance(@PathVariable Long id) {
        seanceService.deleteSeance(id);
    }
    // Seance par Module
    @GetMapping("/module/{moduleId}")
    public List<Seance> getSeancesByModule(@PathVariable Long moduleId) {
        return seanceService.getSeancesByModule(moduleId);
    }

    // Seance par Professeur
    @GetMapping("/professeur/{professeurId}")
    public List<Seance> getSeancesByProfesseur(@PathVariable Long professeurId) {
        return seanceService.getSeancesByProfesseur(professeurId);
    }

    //Exister une séance à une date et heure donnée
    @GetMapping("/existe")
    public boolean existeSeance(@RequestParam Long moduleId, @RequestParam LocalDate dateSeance, @RequestParam LocalTime heureDebut, @RequestParam LocalTime heureFin) {
        return seanceService.existeSeance(moduleId, dateSeance, heureDebut, heureFin);
    }



    //Methode pour annuler une seance
    @PatchMapping("/{id}")
    public ResponseEntity<Seance> annulerSeance(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Optional<Seance> optionalSeance = seanceRepository.findById(id);
        if (optionalSeance.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Seance seance = optionalSeance.get();

        if (updates.containsKey("estAnnulee")) {
            seance.setEstAnnulee((Boolean) updates.get("estAnnulee"));
        }
       

        seanceRepository.save(seance);
        return ResponseEntity.ok(seance);
    }


    //Methode pour recuperer tout le volume horaire programmé et 
    @GetMapping("/volume-horaire")
    public int getTotalVolumeHoraireDeroule() {
        return seanceService.getTotalVolumeHoraireDeroule();
    }




  
}