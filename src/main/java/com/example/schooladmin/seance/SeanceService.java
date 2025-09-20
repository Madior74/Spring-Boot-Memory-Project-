package com.example.schooladmin.seance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.anneeAcademique.AnneeAcademique;
import com.example.schooladmin.anneeAcademique.AnneeAcademiqueRepository;
import com.example.schooladmin.anneeAcademique.AnneeAcademiqueService;
import com.example.schooladmin.assiduite.Assiduite;
import com.example.schooladmin.assiduite.AssiduiteRepository;
import com.example.schooladmin.assiduite.StatutPresence;
import com.example.schooladmin.coursModule.ModuleRepository;
import com.example.schooladmin.etudiant.etudiant.Etudiant;
import com.example.schooladmin.etudiant.etudiant.EtudiantRepository;
import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.professeur.ProfesseurRepository;
import com.example.schooladmin.salle.SalleRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeanceService {

    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ProfesseurRepository professeurRepository;
    @Autowired
    private AnneeAcademiqueRepository anneeAcademiqueRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private AssiduiteRepository assiduiteRepository;

    @Autowired
    private AnneeAcademiqueService anneeAcademiqueService;

    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Optional<Seance> getSeanceById(Long id) {
        return seanceRepository.findById(id);
    }

    // Creation d'une seance avec assiduite par defaut
    
public Seance createSeance(SeanceDTO dto) {
    if (seanceRepository.existsByModuleIdAndDateSeanceAndHeureDebutAndHeureFin(
            dto.getModuleId(), dto.getDateSeance(), dto.getHeureDebut(), dto.getHeureFin())) {
        throw new RuntimeException("Une séance existe déjà à cette date et heure.");
    }
            //Verification de l'existence dune anne active
    AnneeAcademique activatedAnnee=anneeAcademiqueService.getActiveAnneeAcademique();
    if(activatedAnnee==null){
        throw new RuntimeException("Aucune année académique active. Veuillez activer une année avant de créer une séance.");
    }
  

    Seance seance = new Seance();
    seance.setDateSeance(dto.getDateSeance());
    seance.setHeureDebut(dto.getHeureDebut());
    seance.setHeureFin(dto.getHeureFin());
    seance.setEstEnLigne(dto.isEstEnLigne());
    seance.setCreePar(SecurityContextHolder.getContext().getAuthentication().getName());
    seance.setDateCreation(LocalDateTime.now());
    seance.setAnneeAcademique(anneeAcademiqueService.getActiveAnneeAcademique());
    // Salle si présentielle
    if (!dto.isEstEnLigne() && dto.getSalleId() != null) {
        seance.setSalle(salleRepository.findById(dto.getSalleId()).orElse(null));
    }
    seance.setModule(moduleRepository.findById(dto.getModuleId()).orElseThrow());
    seance.setProfesseur(professeurRepository.findById(dto.getProfesseurId()).orElseThrow());
   
    // Sauvegarde de la séance
    Seance createdSeance = seanceRepository.save(seance);

    // 🔹 Récupérer tous les étudiants du niveau du module
    Niveau niveau = createdSeance.getModule().getUe().getSemestre().getNiveau();
    List<Etudiant> etudiants = etudiantRepository.findByNiveauId(niveau.getId());

    // 🔹 Créer assiduités par défaut
    List<Assiduite> assiduites = new ArrayList<>();
    for (Etudiant e : etudiants) {
        Assiduite a = new Assiduite();
        a.setSeance(createdSeance);
        a.setEtudiant(e);
        a.setStatutPresence(StatutPresence.PRESENT); // par défaut
        a.setDatecreation(LocalDateTime.now());
        a.setCreerPar(seance.getCreePar());
        assiduites.add(a);
    }

    assiduiteRepository.saveAll(assiduites);

    return createdSeance;
}


    // Mettre à jour une séance
    public Seance updateSeance(Long id, SeanceDTO dto) {
        Optional<Seance> existingSeance = seanceRepository.findById(id);
        //Verification de l'existence dune anne active

        if (existingSeance.isPresent()) {
            Seance seance = existingSeance.get();
            seance.setDateSeance(dto.getDateSeance());
            seance.setHeureDebut(dto.getHeureDebut());
            seance.setHeureFin(dto.getHeureFin());
            seance.setEstEnLigne(dto.isEstEnLigne());
            if (dto.isEstEnLigne() == false) {
                seance.setSalle(salleRepository.findById(dto.getSalleId()).orElse(null));

            }
            seance.setModule(moduleRepository.findById(dto.getModuleId()).orElse(null));
            seance.setProfesseur(professeurRepository.findById(dto.getProfesseurId()).orElse(null));
            return seanceRepository.save(seance);
        }
        return null;
    }

    // Supprimer une séance
    public void deleteSeance(Long id) {
        seanceRepository.deleteById(id);
    }

    // Seance par Module
    public List<Seance> getSeancesByModule(Long moduleId) {
        return seanceRepository.findByModuleId(moduleId);
    }

    // Seance par Professeur
    public List<Seance> getSeancesByProfesseur(Long professeurId) {
        return seanceRepository.findByProfesseurId(professeurId);
    }

    // Exister une séance à une date et heure donnée
    public boolean existeSeance(Long moduleId, LocalDate dateSeance, LocalTime heureDebut, LocalTime heureFin) {
        return seanceRepository.existsByModuleIdAndDateSeanceAndHeureDebutAndHeureFin(moduleId, dateSeance, heureDebut,
                heureFin);
    }

    // Methode pour recuperer tout le volume horaire programmé et
    public int getTotalVolumeHoraireDeroule() {
        return seanceRepository.findAll().stream()
                .filter(s -> !s.isEstAnnulee())
                .mapToInt(Seance::getDureeEnHeures)
                .sum();
    }

    // Existence d'une Salle
}