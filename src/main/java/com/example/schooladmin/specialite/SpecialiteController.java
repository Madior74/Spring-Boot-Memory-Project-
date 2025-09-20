package com.example.schooladmin.specialite;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/admin/specialites")
public class SpecialiteController {
    @Autowired
    private SpecialiteService specialiteService;

   



    //Ajouter une Specialite 
   
    @PostMapping("/save")
    public ResponseEntity<Specialite> ajouterSpecialite(@RequestBody Specialite specialite){
        Specialite savedSpecialite = specialiteService.ajouterSpecialite(specialite);
        return new ResponseEntity<>(savedSpecialite, HttpStatus.CREATED);
    }
    //Recuperer une specialite par id
    @GetMapping("/{id}")
    public ResponseEntity<Specialite> getSpecialiteById(@PathVariable("id") Long id){
        Optional<Specialite> specialite = specialiteService.getSpecialiteById(id);
        return specialite.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    //Recuperer toutes les specialites
    @GetMapping
    public List<Specialite> getAllSpecialites(){
        return specialiteService.getAllSpecialite();
    }
    
   
   
    //mettre a jour
    @PutMapping("/update/{id}")
    public ResponseEntity<Specialite> updateSpecialite(@PathVariable Long id,  @RequestBody Specialite specialite){
        if(specialiteService.getSpecialiteById(id).isPresent()){

            specialite.setId(id);
            return ResponseEntity.ok(specialiteService.ajouterSpecialite(specialite));
        }

        return ResponseEntity.notFound().build();
    }
  
    

    @DeleteMapping("/{id}")
    public void deleteSpecialite(@PathVariable("id") Long id){
        specialiteService.deleteSpecialite(id);
    }


  
   

}
