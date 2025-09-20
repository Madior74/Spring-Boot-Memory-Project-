package com.example.schooladmin.assiduite;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin/assiduites")
@AllArgsConstructor
public class AssiduiteController {
    private final AssiduiteService assiduiteService;

    // Recuperation
    @GetMapping
    public List<Assiduite> getAllAssiduites() {
        return assiduiteService.getAllAssiduites();
    }

    // //Assiduite By Seance
    // @GetMapping("/seance/{seanceId}")
    // public List<Assiduite> getAssiduitesBySeance(@PathVariable Long seanceId){
    // return assiduiteService.getAssiduitesBySeance(seanceId);
    // }
    // Assiduite By Seance avec dto
    @GetMapping("/seance/{seanceId}")
    public ResponseEntity<List<AssiduiteDTO>> getAssiduitesBySeance(@PathVariable Long seanceId) {
        List<Assiduite> assiduites = assiduiteService.getAssiduitesBySeance(seanceId);
        List<AssiduiteDTO> dtos = assiduites.stream().map(AssiduiteDTO::new).toList();

        return ResponseEntity.ok(dtos);
    }

    // get Assiduite by Id
    @GetMapping("/{id}")
    public ResponseEntity<Assiduite> getAssiduiteById(@PathVariable Long id) {
        Optional<Assiduite> assiduite = assiduiteService.getAssiduiteById(id);
        return assiduite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ajout
    @PostMapping("/save")
    public ResponseEntity<Assiduite> ajouterAssiduite(@RequestBody Assiduite assiduite) {

        Assiduite savedAssiduite = assiduiteService.ajouterAssiduite(assiduite);

        return new ResponseEntity<>(savedAssiduite, HttpStatus.CREATED);
    }

    // Mise a jour
    @PutMapping("/update/{id}")
    public AssiduiteDTO updateAssiduite(@PathVariable long id, @RequestBody AssiduiteDTO assiduiteDTO) {
        Assiduite updatedAssiduite = assiduiteService.updateAssiduite(id, assiduiteDTO);
        return new AssiduiteDTO(updatedAssiduite);
    }

    // Supprimer une Assiduite
    @DeleteMapping("/delete/{id}")
    public void deleteAssiduite(@PathVariable long id) {
        assiduiteService.deleteAssiduite(id);
    }

}
