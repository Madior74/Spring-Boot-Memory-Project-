package com.example.schooladmin.niveau;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.schooladmin.dto.FiliereDTO;
import com.example.schooladmin.dto.NiveauDTO;
import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.filiere.FiliereService;
import com.example.schooladmin.semestre.Semestre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/admin/niveaux")
public class NiveauController {
    @Autowired
    private NiveauService niveauService;

      @Autowired
    private FiliereService filiereService;

  


    @GetMapping
    public List<Niveau> getAllNiveaux() {
        return niveauService.getAllNiveaux();
    }

    // @GetMapping("/{id}")
    // public Niveau getNiveauById(@PathVariable Long id) {    
    //     return niveauService.getNiveauById(id);
    // }




    @DeleteMapping("/{id}")
    public void deleteNiveau(@PathVariable Long id) {
        niveauService.deleteNiveau(id);
    }

      @GetMapping("/{id}/semestres")
    public List<Semestre> getSemestresByNiveau(@PathVariable Long id) {
        return niveauService.getSemestresByNiveau(id);
    }


    //Niveaux pour chaque filiere
   
    @GetMapping("/filiere/{filiereId}")
public ResponseEntity<List<NiveauDTO>> getNiveauxByFiliere(@PathVariable Long filiereId) {
    List<Niveau> niveaux = niveauService.getNiveauxByFiliere(filiereId);
    List<NiveauDTO> niveauxDTO = niveaux.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    return ResponseEntity.ok(niveauxDTO);
}

// Méthode pour convertir un objet Niveau en DTO
private NiveauDTO convertToDTO(Niveau niveau) {
    NiveauDTO dto = new NiveauDTO();
    dto.setId(niveau.getId());
    dto.setNomNiveau(niveau.getNomNiveau());

    if (niveau.getFiliere() != null) {
        dto.setFiliere(new FiliereDTO(
                niveau.getFiliere().getId(),
                niveau.getFiliere().getNomFiliere()
        ));
    }

    return dto;
}
    
    
    @PostMapping("/filiere/{filiereId}")
    public ResponseEntity<?> addNiveauToFiliere(@PathVariable Long filiereId, @RequestBody Niveau niveauDetails) {
    // Vérifier si la filière existe
    Filiere filiere = filiereService.findById(filiereId);
    if (filiere == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Filière non trouvée");
    }

    // Vérifier si le niveau existe déjà dans cette filière
    boolean niveauExiste = niveauService.existsByNomNiveauAndFiliere(niveauDetails.getNomNiveau(), filiere);
    if (niveauExiste) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Ce niveau existe déjà dans cette filière");
    }

    // Créer le niveau
    niveauDetails.setFiliere(filiere);
    Niveau savedNiveau = niveauService.saveNiveau(niveauDetails);

    return ResponseEntity.status(HttpStatus.CREATED).body(savedNiveau);
   }

      // Vérifier si un niveau existe par ID
    // Vérifier si un niveau existe pour une filière donnée
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkNiveauExists(
            @RequestParam String nomNiveau,
            @RequestParam Long filiereId) {
        boolean exists = niveauService.niveauExists(nomNiveau, filiereId);
        return ResponseEntity.ok(exists);
    }


//Pour recuperer l'id d'une filiere
    @GetMapping("/{niveauId}/filiere")
public ResponseEntity<Filiere> getFiliereByNiveauId(@PathVariable Long niveauId) {
    // Récupérer le niveau
    Niveau niveau = niveauService.getNiveauById(niveauId);
    if (niveau == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Récupérer la filière associée au niveau
    Filiere filiere = niveau.getFiliere();
    if (filiere == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(filiere);
}

//Nombre d'etudiant
@GetMapping("/{id}/etudiants/count")
    public ResponseEntity<Map<String, Integer>> getEtudiantsCount(@PathVariable Long id) {
        int count = niveauService.getEtudiantCountByNiveauId(id);
        Map<String, Integer> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }





}
