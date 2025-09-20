package com.example.schooladmin.filiere;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.schooladmin.anneeAcademique.AnneeAcademiqueRepository;
import com.example.schooladmin.etudiant.etudiant.EtudiantRepository;
import com.example.schooladmin.niveau.Niveau;
import com.example.schooladmin.niveau.NiveauRepository;
import com.example.schooladmin.semestre.Semestre;
import com.example.schooladmin.semestre.SemestreRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import org.apache.commons.lang3.tuple.Pair;



import jakarta.transaction.Transactional;
import lombok.Data;





@Data
@Service
public class FiliereService {
    @Autowired
    private FiliereRepository filiereRepository;

    @Autowired
    private AnneeAcademiqueRepository anneeAcademiqueRepository;

    @Autowired
    private NiveauRepository niveauRepository;

    @Autowired
    private SemestreRepository semestreRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;


    //

    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere getFiliereById(Long id) {
        return filiereRepository.findById(id).orElseThrow(() -> new RuntimeException("Filière non trouvée"));
    }


    public void deleteFiliere(final Long id) {
        filiereRepository.deleteById(id);
    }

    public List<Niveau> getNiveauxByFiliere(Long filiereId) {
        Filiere filiere = filiereRepository.findById(filiereId)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée"));
        return filiere.getNiveaux();
    }

     //
     public Filiere findById(Long id) {
        return filiereRepository.findById(id).orElse(null);
    }

//Auto 
public Filiere createFiliereAvecStructure(FiliereRequest request) {
    Optional<Filiere> existingFiliere = filiereRepository.findByNomFiliereIgnoreCase(request.getNomFiliere().trim());
    if (existingFiliere.isPresent()) {
        throw new RuntimeException("Une filière portant le nom " + request.getNomFiliere() + " existe déjà.");
    }

    Filiere filiere = new Filiere();
    filiere.setNomFiliere(request.getNomFiliere());
    filiere.setDescription(request.getDescription());
    filiere.setCreePar(SecurityContextHolder.getContext().getAuthentication().getName());
    filiere.setDateCreation(new Date());


    filiere.setActif(true);

    filiere = filiereRepository.save(filiere);

    List<Pair<String, List<String>>> structure = List.of(
        Pair.of("Licence 1", List.of("Semestre 1", "Semestre 2")),
        Pair.of("Licence 2", List.of("Semestre 3", "Semestre 4")),
        Pair.of("Licence 3", List.of("Semestre 5", "Semestre 6"))
    );

    for (Pair<String, List<String>> entry : structure) {
        Niveau niveau = new Niveau();
        niveau.setNomNiveau(entry.getLeft());
        niveau.setFiliere(filiere);
        niveau = niveauRepository.save(niveau);

        for (String nomSemestre : entry.getRight()) {
            Semestre semestre = new Semestre();
            semestre.setNomSemestre(nomSemestre);
            semestre.setNiveau(niveau);
            semestreRepository.save(semestre);
        }
    }

    return filiere;
}


// Mettre à jour une filière
public Filiere updateFiliere(Long id, Filiere updatedFiliere) {
    Filiere filiere = filiereRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filière non trouvée"));    

    filiere.setNomFiliere(updatedFiliere.getNomFiliere());
    filiere.setDescription(updatedFiliere.getDescription());
    filiere.setModifiePar(updatedFiliere.getModifiePar());
    filiere.setDateModification(LocalDateTime.now());

    return filiereRepository.save(filiere);
}
    //Nombre detudiant par filiere
    public int getEtudiantsCountByFiliereId(Long filiereId) {
        return etudiantRepository.countEtudiantsByFiliereId(filiereId);
    }


    

    //Ver
    public boolean existsByNomFiliere(String nomFiliere) {
    return filiereRepository.existsByNomFiliere(nomFiliere);
}

}
        
