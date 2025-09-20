package com.example.schooladmin.etudiant.candiddat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.schooladmin.ue.UniteEnseignementRepository;
import lombok.Data;

@Data
@Service
public class CandidatService {

    @Autowired
    private  UniteEnseignementRepository ueRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidatRepository candidatPreInscritRepository;
    
    @Autowired
    private com.example.schooladmin.region.RegionRepository regionRepository;
    
    @Autowired
    private com.example.schooladmin.region.departement.DepartementRepository departementRepository;
    
    @Autowired
    private com.example.schooladmin.filiere.FiliereRepository filiereRepository;
    
    @Autowired
    private com.example.schooladmin.niveau.NiveauRepository niveauRepository;
    
    @Autowired
    private com.example.schooladmin.anneeAcademique.AnneeAcademiqueRepository anneeAcademiqueRepository;

    public Candidat ajouterEtudiant(Candidat etudiant) {
        // Vérification que l'étudiant n'existe pas déjà (par CNI ou INE)
        if (candidatPreInscritRepository.findByCniOrIne(etudiant.getCni(), etudiant.getIne()) != null) {
            throw new RuntimeException("Un étudiant avec ce CNI ou INE existe déjà.");
        }
        
        // Charger les relations si elles sont null mais que les IDs sont présents
        if (etudiant.getRegion() != null && etudiant.getRegion().getId() != null) {
            etudiant.setRegion(regionRepository.findById(etudiant.getRegion().getId())
                .orElseThrow(() -> new RuntimeException("Région non trouvée")));
        }
        
        if (etudiant.getDepartement() != null && etudiant.getDepartement().getId() != null) {
            etudiant.setDepartement(departementRepository.findById(etudiant.getDepartement().getId())
                .orElseThrow(() -> new RuntimeException("Département non trouvé")));
        }
        
        if (etudiant.getFiliereSouhaitee() != null && etudiant.getFiliereSouhaitee().getId() != null) {
            etudiant.setFiliereSouhaitee(filiereRepository.findById(etudiant.getFiliereSouhaitee().getId())
                .orElseThrow(() -> new RuntimeException("Filière non trouvée")));
        }
        
        if (etudiant.getNiveauSouhaite() != null && etudiant.getNiveauSouhaite().getId() != null) {
            etudiant.setNiveauSouhaite(niveauRepository.findById(etudiant.getNiveauSouhaite().getId())
                .orElseThrow(() -> new RuntimeException("Niveau non trouvé")));
        }

        if (etudiant.getAnneeAcademique() != null && etudiant.getAnneeAcademique().getId() != null) {
            etudiant.setAnneeAcademique(anneeAcademiqueRepository.findById(etudiant.getAnneeAcademique().getId())
                .orElseThrow(() -> new RuntimeException("Année académique non trouvée")));
        }

        String motDePasseHache = passwordEncoder.encode(etudiant.getPassword());
        etudiant.setPassword(motDePasseHache);
        etudiant.setCreerPar(SecurityContextHolder.getContext().getAuthentication().getName());
        
        // Définir la date d'ajout
        etudiant.setDateAjout(LocalDateTime.now());
        return candidatPreInscritRepository.save(etudiant);
    }

    // Méthode pour récupérer tous les étudiants
    public List<Candidat> recupererTousLesEtudiants() {
        return candidatPreInscritRepository.findAll();
    }

    // Méthode pour récupérer un étudiant par son ID
    public Optional<Candidat> recupererEtudiantParId(Long id) {
        return candidatPreInscritRepository.findById(id);
    }

    // Méthode pour supprimer un étudiant par son ID
    public void supprimerEtudiant(Long id) {
        candidatPreInscritRepository.deleteById(id);
    }

    /////
    public List<Candidat> getAllEtudiants() {
        return candidatPreInscritRepository.findAll();
    }

    public Optional<Candidat> getEtudiantById(Long id) {
        return candidatPreInscritRepository.findById(id);
    }

    public void deleteEtudiant(Long id) {
        candidatPreInscritRepository.deleteById(id);
    }

    // Existence de l'email
    public boolean emailExists(String email) {
        return candidatPreInscritRepository.existsByEmail(email);
    }

   

    // // Méthode pour compter les étudiants par ID de filière
    // public long countEtudiantsByFiliereId(Long filiereId) {
    // return candidatPreInscritRepository.countByFiliereId(filiereId);
    // }

    // Méthode pour récupérer les étudiants ayant exactement 3 documents
    public List<Candidat> getEtudiantsAvecTroisDocuments() {
        return candidatPreInscritRepository.findCandidatWithThreeDocuments();
    }

    // Mettre a Jour
    public Candidat updateEtudiant(Candidat etudiant) {
        // Vérification si l'étudiant existe dans la base de données
        Optional<Candidat> existingEtudiantOptional = candidatPreInscritRepository
                .findById(etudiant.getId());
        if (!existingEtudiantOptional.isPresent()) {
            throw new RuntimeException("Étudiant non trouvé avec l'ID : " + etudiant.getId());
        }
        // Récupération de l'étudiant existant
        Candidat existingEtudiant = existingEtudiantOptional.get();

        // Mise à jour des champs modifiables
        existingEtudiant.setPrenom(etudiant.getPrenom());
        existingEtudiant.setNom(etudiant.getNom());
        existingEtudiant.setAdresse(etudiant.getAdresse());
        existingEtudiant.setTelephone(etudiant.getTelephone());
        existingEtudiant.setSexe(etudiant.getSexe());
        existingEtudiant.setEmail(etudiant.getEmail());
        existingEtudiant.setImagePath(etudiant.getImagePath());
        existingEtudiant.setPaysDeNaissance(etudiant.getPaysDeNaissance());
        existingEtudiant.setDateDeNaissance(etudiant.getDateDeNaissance());
        existingEtudiant.setCni(etudiant.getCni());
        existingEtudiant.setIne(etudiant.getIne());

        // Mise à jour des relations
        existingEtudiant.setNiveauSouhaite(etudiant.getNiveauSouhaite());
        existingEtudiant.setFiliereSouhaitee(etudiant.getFiliereSouhaitee());
        existingEtudiant.setDepartement(etudiant.getDepartement());
        

        // Sauvegarde des modifications
        return candidatPreInscritRepository.save(existingEtudiant);
    }

}
