package com.example.schooladmin.annonce;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.activity.ActivityLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/annonces")
@RequiredArgsConstructor
public class AnnonceController {

    private final AnnonceService annonceService;
    private final ActivityLogService activityLogService;

    @PostMapping
    public ResponseEntity<Annonce> create(@RequestBody AnnonceRequest request) {
        Annonce created = annonceService.create(request);
        activityLogService.log("ANNONCE", "Création d'une annonce : " + created.getTitre());
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Annonce> update(@PathVariable Long id, @RequestBody AnnonceRequest request) {
        Annonce updated = annonceService.update(id, request);
        activityLogService.log("ANNONCE", "Mise à jour annonce (id=" + id + ") : " + updated.getTitre());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Annonce> getById(@PathVariable Long id) {
        return ResponseEntity.ok(annonceService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Annonce>> listActives() {
        return ResponseEntity.ok(annonceService.listActives());
    }

    @GetMapping("/generales")
    public ResponseEntity<List<Annonce>> listGenerales() {
        return ResponseEntity.ok(annonceService.listActivesGenerales());
    }

    @GetMapping("/niveau/{niveauId}")
    public ResponseEntity<List<Annonce>> listByNiveau(@PathVariable Long niveauId) {
        return ResponseEntity.ok(annonceService.listActivesByNiveau(niveauId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        annonceService.delete(id);
        activityLogService.log("ANNONCE", "Suppression annonce (id=" + id + ")");
        return ResponseEntity.noContent().build();
    }
} 