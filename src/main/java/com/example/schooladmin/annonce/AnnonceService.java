package com.example.schooladmin.annonce;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.niveau.NiveauRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnnonceService {

    private final AnnonceRepository annonceRepository;
    private final NiveauRepository niveauRepository;

    public Annonce create(AnnonceRequest request) {
        validateRequest(request);
        Annonce annonce = new Annonce();
        applyRequestToAnnonce(annonce, request);
        annonce.setCreePar(SecurityContextHolder.getContext().getAuthentication().getName());
        annonce.setDateCreation(LocalDateTime.now());
        return annonceRepository.save(annonce);
    }

    public Annonce update(Long id, AnnonceRequest request) {
        validateRequest(request);
        Annonce annonce = annonceRepository.findById(id).orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
        applyRequestToAnnonce(annonce, request);
        annonce.setDateModification(LocalDateTime.now());
        return annonceRepository.save(annonce);
    }

    public void delete(Long id) {
        annonceRepository.deleteById(id);
    }

    public Annonce getById(Long id) {
        return annonceRepository.findById(id).orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
    }

    public List<Annonce> listActives() {
        return annonceRepository.findByEstActiveTrueOrderByDatePublicationDesc();
    }

    public List<Annonce> listActivesByNiveau(Long niveauId) {
        Niveau niveau = niveauRepository.findById(niveauId).orElseThrow(() -> new RuntimeException("Niveau non trouvé"));
        return annonceRepository.findByNiveauAndEstActiveTrueOrderByDatePublicationDesc(niveau);
    }

    public List<Annonce> listActivesGenerales() {
        return annonceRepository.findByAnnonceGeneraleTrueAndEstActiveTrueOrderByDatePublicationDesc();
    }

    public void deactivateExpired() {
        LocalDate today = LocalDate.now();
        List<Annonce> expired = annonceRepository.findByDateExpirationBefore(today);
        for (Annonce a : expired) {
            a.setEstActive(false);
        }
        annonceRepository.saveAll(expired);
    }

    private void validateRequest(AnnonceRequest request) {
        if (request.getTitre() == null || request.getTitre().isBlank()) {
            throw new RuntimeException("Le titre est obligatoire");
        }
        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new RuntimeException("Le message est obligatoire");
        }
        if (request.getAnnonceGenerale() == null) {
            throw new RuntimeException("Le champ annonceGenerale est obligatoire");
        }
        if (!request.getAnnonceGenerale() && request.getNiveauId() == null) {
            throw new RuntimeException("niveauId est requis pour une annonce non générale");
        }
        if (request.getDatePublication() == null) {
            request.setDatePublication(LocalDate.now());
        }
        if (request.getDateExpiration() != null && request.getDateExpiration().isBefore(request.getDatePublication())) {
            throw new RuntimeException("La date d'expiration doit être après la date de publication");
        }
    }

    private void applyRequestToAnnonce(Annonce annonce, AnnonceRequest request) {
        annonce.setTitre(request.getTitre());
        annonce.setMessage(request.getMessage());
        annonce.setDatePublication(request.getDatePublication());
        annonce.setDateExpiration(request.getDateExpiration());
        annonce.setAnnonceGenerale(Boolean.TRUE.equals(request.getAnnonceGenerale()));
        if (Boolean.TRUE.equals(request.getAnnonceGenerale())) {
            annonce.setNiveau(null);
        } else {
            Niveau niveau = niveauRepository.findById(request.getNiveauId())
                .orElseThrow(() -> new RuntimeException("Niveau non trouvé"));
            annonce.setNiveau(niveau);
        }
        annonce.setEstActive(request.getEstActive() == null ? true : request.getEstActive());
    }
} 