package com.example.schooladmin.salle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/admin/salles")
public class SalleController {

    @Autowired
    private SalleService salleService;

    @Autowired
    private SalleRepository salleRepository;

    //get
    @GetMapping
    public List<Salle> getAllSalles(){
        return salleService.getAllSalles();
    }


       // Version maintenant
    @GetMapping("/statut-now")
    public List<Salle> getSallesAvecStatutNow() {
        return salleService.getAllSallesWithStatutNow();
    }

       @GetMapping("/statut-detaille")
    public List<SalleStatutDTO> getSallesStatutDetaille() {
        return salleService.getAllSallesWithStatutDetaille();
    }
    

    //Create
   @PostMapping("/save")
public ResponseEntity<?> creerSalle(@RequestBody Salle salle, BindingResult result) {
    if (result.hasErrors()) {
        return ResponseEntity.badRequest().body(result.getAllErrors());
    }
    salleRepository.save(salle);
    return ResponseEntity.ok(salle);
}   




    @DeleteMapping("/{id}")
    public void deleteSalle(@PathVariable("id") Long id){
        salleService.deleteSalle(id);
    }


  
    // Existence
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkSeanceExist(@RequestParam String nomSalle) {
        boolean exists = salleService.existsByNomSalle(nomSalle);
        return ResponseEntity.ok(exists);
    }


    // Vérifier si salle utilisée par une séance ou évaluation
    @GetMapping("/is-used")
    public ResponseEntity<Boolean> checkSalleUsed(@RequestParam Long salleId,
                                                   @RequestParam LocalDate date,
                                                   @RequestParam LocalTime heureDebut,
                                                   @RequestParam LocalTime heureFin) {
        boolean isUsed = salleService.isSalleUsed(salleId, date, heureDebut, heureFin);
        return ResponseEntity.ok(isUsed);
    }


    


    
}
