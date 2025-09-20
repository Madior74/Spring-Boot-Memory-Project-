package com.example.schooladmin.etudiant.document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.schooladmin.etudiant.candiddat.Candidat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final String uploadDir = "uploads/"; // Répertoire pour stocker les fichiers

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

       

        public Document uploadDocument(MultipartFile file, String nom, String type, Candidat etudiant) throws IOException {
    boolean exists = documentRepository.existsByNomAndCandidat_Id(nom, etudiant.getId());
    if (exists) {
        throw new RuntimeException("Un Document portant le nom " + nom + " existe déjà.");
    }

    // Créer le répertoire s'il n'existe pas
    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath); // Crée le dossier uploads
    }

    // Générer un nom de fichier unique pour éviter les conflits
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    Path filePath = uploadPath.resolve(fileName); // Meilleure pratique

    // Écrire le fichier
    Files.write(filePath, file.getBytes());

    // Créer et sauvegarder le document
    Document document = new Document();
    document.setNom(nom);
    document.setType(type);
    document.setDateDepot(LocalDateTime.now());
    document.setCandidat(etudiant);
    document.setCheminFichier(filePath.toString()); // Sauvegarde le chemin complet

    return documentRepository.save(document);
}

    public List<Document> getDocumentsByEtudiant(Long etudiantId) {
        return documentRepository.findByCandidat_Id(etudiantId);
    }

    public Document getDocument(Long documentId) {
        return documentRepository.findById(documentId).orElse(null);
    }

    //Supprimer un document
    public void deleteDocument(final Long id){
        documentRepository.deleteById(id);
    }
}