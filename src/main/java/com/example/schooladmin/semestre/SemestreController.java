package com.example.schooladmin.semestre;
import java.util.List;

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

import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.niveau.NiveauService;

import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/admin/semestres")
public class SemestreController {
    @Autowired
    private SemestreService semestreService;



    @Autowired
    private NiveauService niveauService;

    @GetMapping
    public List<Semestre> getAllSemestres() {
        return semestreService.getAllSemestres();
    }

    @GetMapping("/{id}")
    public Semestre getSemestreById(@PathVariable Long id) {
        return semestreService.getSemestreById(id);
    }

    
   // Ajouter un semestre à un niveau
    @PostMapping("/{niveauId}/semestre")
    public ResponseEntity<?> addSemestreToNiveau(
            @PathVariable Long niveauId, 
            @RequestBody Semestre semestreDetails) {

        // Vérifier si le niveau existe
        Niveau niveau = niveauService.getNiveauById(niveauId);
        if (niveau == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Niveau non trouvé");
        }

        // Associer la filière au semestre
        semestreDetails.setNiveau(niveau);

        // Enregistrer le semestre
        Semestre savedSemestre = semestreService.saveSemestre(semestreDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedSemestre);
    }



    @DeleteMapping("/{id}")
    public void deleteSemestre(@PathVariable Long id) {
        semestreService.deleteSemestre(id);
    }



    //Semestre Pour chaque Niveau
    @GetMapping("/niveau/{niveauId}")
    public List<Semestre> getSemestreByNiveau(@PathVariable Long niveauId){
        return semestreService.getSemestreByNiveau(niveauId);
    }


    //Verifier si un semestre existe deja
    @GetMapping("/exists")
   public ResponseEntity<Boolean> checkSemestreExists(@RequestParam String nomSemestre,@RequestParam long niveauId){
    boolean exists=semestreService.semestreExist(nomSemestre,niveauId);
    return ResponseEntity.ok(exists);
   }
    
    

}
