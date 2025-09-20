package com.example.schooladmin.ue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.schooladmin.coursModule.CourseModule;
import com.example.schooladmin.exception.UEException;

import java.util.List;
import java.util.Optional;

@Service
public class UniteEnseignementService {

    @Autowired
    private UniteEnseignementRepository ueRepository;

    
    public List<UniteEnseignement> getAllUEs() {
        return ueRepository.findAll();
    }

  
    public UniteEnseignement getUEBy(Long id) {
        return ueRepository.findById(id)
                .orElseThrow(() -> new UEException("UE non trouvée avec l'id : " + id));
    }

 
    public Optional<UniteEnseignement> getUEById(Long ueId) {
        return ueRepository.findById(ueId);
    }

  
    public UniteEnseignement saveUE(UniteEnseignement ue) {
        validateUE(ue); // Validation des données
        checkIfUEExists(ue.getNomUE(), ue.getSemestre().getId()); // Vérification d'unicité
        return ueRepository.save(ue);
    }

    
    public UniteEnseignement addModuleToUE(Long ueId, CourseModule module) {
        UniteEnseignement ue = ueRepository.findById(ueId)
                .orElseThrow(() -> new UEException("UE non trouvée avec l'id : " + ueId));

        if (ue.getModules().size() >= 4) {
            throw new UEException("Une UE ne peut pas avoir plus de 4 modules.");
        }

        ue.addModule(module);
        return ueRepository.save(ue);
    }

   
    public void deleteUE(Long id) {
        ueRepository.deleteById(id);
    }

 
    public List<UniteEnseignement> getUEBySemestre(Long semestreId) {
        return ueRepository.findBySemestreId(semestreId);
    }


    public boolean ueExist(String nomUE, Long semestreId) {
        return ueRepository.existsByNomUEAndSemestreId(nomUE, semestreId);
    }


    public UniteEnseignement findById(Long ueId) {
        return ueRepository.findById(ueId).orElse(null);
    }

 
    public Page<UniteEnseignement> getAllUEs(Pageable pageable) {
        return ueRepository.findAll(pageable);
    }

 

    private void validateUE(UniteEnseignement ue) {
        if (ue.getSemestre() == null) {
            throw new IllegalArgumentException("L'UE doit être associée à un semestre.");
        }
        if (ue.getNomUE() == null || ue.getNomUE().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'UE ne peut pas être vide.");
        }
        if (ue.getCodeUE() == null || ue.getCodeUE().isEmpty()) {
            throw new IllegalArgumentException("Le code de l'UE ne peut pas être vide.");
        }
    }


    private void checkIfUEExists(String nomUE, Long semestreId) {
        if (ueExist(nomUE, semestreId)) {
            throw new UEException("Une UE avec ce nom existe déjà dans ce semestre.");
        }
    }
}