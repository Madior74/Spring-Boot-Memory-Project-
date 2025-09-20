package com.example.schooladmin.etudiant.document;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.schooladmin.etudiant.candiddat.Candidat;
import com.example.schooladmin.etudiant.candiddat.CandidatRepository;

import lombok.AllArgsConstructor;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/admin/documents")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final CandidatRepository candidatPreInscritRepository;

 
   @PostMapping("/upload")
public ResponseEntity<?> uploadDocument(
        @RequestParam("file") MultipartFile file,
        @RequestParam("nom") String nom,
        @RequestParam("etudiantId") Long etudiantId) {

    try {
        Candidat etudiant = candidatPreInscritRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'ID : " + etudiantId));

        Document document = documentService.uploadDocument(file, nom, file.getContentType(), etudiant);
        return new ResponseEntity<>(document, HttpStatus.CREATED);

    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body(Map.of("error", "Erreur de lecture/écriture du fichier", "details", e.getMessage()));
    } catch (Exception e) {
        e.printStackTrace(); // 🔥 Cette ligne est cruciale
        return ResponseEntity.status(500).body(Map.of("error", "Erreur serveur", "details", e.getMessage()));
    }
}

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Document>> getDocumentsByEtudiant(@PathVariable Long etudiantId) {
        List<Document> documents = documentService.getDocumentsByEtudiant(etudiantId);
        System.out.println(documents);
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long documentId) {
        Document document = documentService.getDocument(documentId);
        if (document == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Path path = Paths.get(document.getCheminFichier());
            byte[] data = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + document.getNom() + "\"")
                    .body(data);
        } catch (IOException e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);
    }
}