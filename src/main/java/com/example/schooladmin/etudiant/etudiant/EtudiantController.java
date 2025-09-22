package com.example.schooladmin.etudiant.etudiant;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.dto.FcmTokenDTO;

@RestController
@RequestMapping("/api/admin/inscriptions")
public class EtudiantController {

    @Autowired
    private EtudiantService etudiantService;

    @Autowired
    private EtudiantRepository etudiantRepository;

    // Recuperer toutes les inscriptions
    @GetMapping("/all")
    public List<Etudiant> getAllInscriptions(){
    return etudiantRepository.findAll();
    }

    // Recuperer toutes les inscriptions avec DTO
    @GetMapping
    public ResponseEntity<List<EtudiantDTO>> getAllIncriptions(){
        List<Etudiant> inscriptions=etudiantRepository.findAll();
        List<EtudiantDTO> dto=inscriptions.stream().map(EtudiantDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(dto);
    }

    // Creer un nouveau Etudint
    @PostMapping("/save")
    public ResponseEntity<?> inscrireEtudiant(@RequestBody Etudiant inscription) {
        try {
            Etudiant saved = etudiantService.ajouterInscription(inscription);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

    // Supprimer une inscription
    @DeleteMapping("/{id}")
    public void deleteInscription(@PathVariable Long id) {
        etudiantRepository.deleteById(id);
    }

    // Methode Existe ou NOn
    @GetMapping("/check")
    public boolean checkIfInscriptionExists(
            @RequestParam Long etudiantId,
            @RequestParam Long filiereId) {
        return etudiantService.checkIfInscriptionExists(etudiantId, filiereId);
    }

    // Recuperer les inscriptions par niveau
    @GetMapping("/niveau/{niveauId}")
    public ResponseEntity<List<EtudiantDTO>> getInscriptionsByNiveau(@PathVariable Long niveauId) {


        List<Etudiant> inscrits= etudiantService.getInscriptionsByNiveauId(niveauId);
        List<EtudiantDTO> dto=inscrits.stream().map(EtudiantDTO::new).collect(Collectors.toList());

        return ResponseEntity.ok(dto);
    }

    // //Recuperer les inscriptions par etudiant
    // @GetMapping("/etudiant/{etudiantId}")
    // public List<Inscription> getInscriptionsByEtudiant(@PathVariable Long etudiantId) {
    //     return etudiantRepository.findByEtudiantId(etudiantId);
    // }

    // //Recuperer les inscriptions par filiere
    // @GetMapping("/filiere/{filiereId}")
    // public List<Inscription> getInscriptionsByFiliere(@PathVariable Long
    // filiereId) {
    // return etudiantRepository.findByFiliereId(filiereId);
    // }

    @PostMapping("/etudiant/{etudiantId}/fcm-token")
public ResponseEntity<Void> saveFcmToken(@PathVariable Long etudiantId, @RequestBody FcmTokenDTO dto) {
    etudiantService.saveFcmToken(etudiantId, dto.getToken());
    return ResponseEntity.ok().build();
}
}
