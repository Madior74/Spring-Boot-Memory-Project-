package com.example.schooladmin.ue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.semestre.Semestre;
import com.example.schooladmin.semestre.SemestreService;

@RestController
@RequestMapping("/api/admin/ues")
public class UniteEnseignementController {

    @Autowired
    private UniteEnseignementRepository ueRepository;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private UniteEnseignementService ueService;

    @Autowired
    private com.example.schooladmin.activity.ActivityLogService activityLogService;

    // Récupérer toutes les UEs
    @GetMapping
    public ResponseEntity<List<UniteEnseignement>> getAllUEs() {
        List<UniteEnseignement> ues = ueRepository.findAll();
        return new ResponseEntity<>(ues, HttpStatus.OK);
    }

    // Récupérer une UE par son ID
    @GetMapping("/{id}")
    public ResponseEntity<UniteEnseignement> getUEById(@PathVariable("id") Long id) {
        Optional<UniteEnseignement> ueData = ueRepository.findById(id);

        if (ueData.isPresent()) {
            return new ResponseEntity<>(ueData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // //UE by Semestre
    @GetMapping("/semestre/{semestreId}")
    public List<UniteEnseignement> getUEsBySemestre(@PathVariable Long semestreId) {
        return ueService.getUEBySemestre(semestreId);
    }





//--------------------------------------------------------------------------------

      //Ajouter une UE a un Semestre
    @PostMapping("/{semestreId}/ue")
    public ResponseEntity<?> addUEToSemestre(@PathVariable Long semestreId, @RequestBody Map<String, Object> body) {
    // Affiche les données reçues pour le débogage
    System.out.println("Données reçues : " + body);

    // Extraction des données du corps de la requête

    Object semestreObj = body.get("semestre");

    // Vérifier si les éléments sont présents et valides
   
    if (semestreObj == null || ((Map) semestreObj).get("id") == null) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Semestre manquant ou invalide");
    }

    // Récupérer les objets Semestre, Niveau et Filiere
    Semestre semestre = semestreService.getSemestreById(semestreId);
    
    // Créer une nouvelle UE
    UniteEnseignement ue = new UniteEnseignement();
    ue.setSemestre(semestre);
  
    // Assigner les autres propriétés
    ue.setNomUE((String) body.get("nomUE"));
    ue.setCodeUE((String) body.get("codeUE"));

    // Conversion de la dateAjout en LocalDateTime
    String dateAjoutStr = (String) body.get("dateAjout");
    if (dateAjoutStr != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateAjout = LocalDateTime.parse(dateAjoutStr, formatter);
        ue.setDateAjout(dateAjout);
    }

    // Enregistrer l'UE
    UniteEnseignement savedUe = ueService.saveUE(ue);
    activityLogService.log("UE", "Ajout d'une UE : " + savedUe.getNomUE());
    // Réponse avec les données de l'UE ajoutée
    return ResponseEntity.status(HttpStatus.CREATED).body(savedUe);
}





    ///Existence de l'UE
    @GetMapping("/exists")
   public ResponseEntity<Boolean> checkUEExist(@RequestParam String nomUE,@RequestParam Long semestreId){
        boolean exists=ueService.ueExist(nomUE, semestreId);

        return ResponseEntity.ok(exists);
   }



    @DeleteMapping("/{id}")
    public void deleteUE(@PathVariable Long id) {
        ueService.deleteUE(id);
        activityLogService.log("UE", "Suppression d'une UE (id=" + id + ")");
    }
//---------------------------------------------------

    // Mettre à jour une UE existante
    @PutMapping("/{ueId}")
    public ResponseEntity<?> updateUE(@PathVariable Long ueId, @RequestBody Map<String, Object> body) {
        // Affiche les données reçues pour le débogage
        System.out.println("Données reçues pour mise à jour : " + body);

        // Vérifier si l'UE existe
        Optional<UniteEnseignement> existingUEOpt = ueService.getUEById(ueId);
        if (!existingUEOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("UE non trouvée");
        }

        // Récupérer l'UE existante
        UniteEnseignement existingUE = existingUEOpt.get();

        // Mettre à jour les propriétés de l'UE
        existingUE.setNomUE((String) body.get("nomUE"));
        existingUE.setCodeUE((String) body.get("codeUE"));

        // Récupérer les objets Niveau, Filière et Semestre si nécessaire
        if (body.get("semestre") != null) {
            Object semestreObjRaw = body.get("semestre");
            if (!(semestreObjRaw instanceof Map)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid semestre object");
            }
            if (semestreObjRaw instanceof Map<?, ?>) {
                Map<String, Object> semestreObj = (Map<String, Object>) semestreObjRaw;
            if (semestreObj.get("id") != null) {
                Semestre semestre = semestreService.getSemestreById(Long.valueOf(semestreObj.get("id").toString()));
                existingUE.setSemestre(semestre);
            }
        }
    }

    // Conversion de la dateAjout en LocalDateTime (si nécessaire)
    String dateAjoutStr = (String) body.get("dateAjout");
    if (dateAjoutStr != null) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        LocalDateTime dateAjout = LocalDateTime.parse(dateAjoutStr, formatter);
        existingUE.setDateAjout(dateAjout);
    }

    // Enregistrer l'UE mise à jour
    UniteEnseignement updatedUE = ueService.saveUE(existingUE);
    activityLogService.log("UE", "Modification d'une UE : " + updatedUE.getNomUE());
    // Réponse avec les données de l'UE mise à jour
    return ResponseEntity.ok(updatedUE);
}


    
}


