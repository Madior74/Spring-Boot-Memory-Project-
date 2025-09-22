package com.example.schooladmin.niveau;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schooladmin.etudiant.etudiant.Etudiant;
import com.example.schooladmin.etudiant.etudiant.EtudiantRepository;
import com.example.schooladmin.filiere.Filiere;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.semestre.Semestre;

import lombok.Data;

@Data
@Service
public class NiveauService {
    @Autowired
    private NiveauRepository niveauRepository;

    @Autowired
    private  EtudiantRepository etudiantRepository;

    public List<Niveau> getAllNiveaux() {
        return niveauRepository.findAll();
    }

    public Niveau getNiveauById(Long id) {
        return niveauRepository.findById(id).orElseThrow(() -> new RuntimeException("Niveau non trouvé"));
    }

    public Niveau saveNiveau(Niveau niveau) {
        return niveauRepository.save(niveau);
    }

    public void deleteNiveau(Long id) {
        niveauRepository.deleteById(id);
    }


      public List<Semestre> getSemestresByNiveau(Long niveauId) {
        Niveau niveau = niveauRepository.findById(niveauId)
                .orElseThrow(() -> new RuntimeException("Niveau non trouvé"));
        return niveau.getSemestres();
    }

    public List<Niveau> getNiveauxByFiliere(Long filiereId) {
        return niveauRepository.findByFiliereId(filiereId);
    }


    //Veification de l'existence 
    public boolean niveauExists(String nomNiveau, Long filiereId) {
        return niveauRepository.existsByNomNiveauAndFiliereId(nomNiveau, filiereId);
    }


    //nombre d'etudiants du niveau
    public int getEtudiantCountByNiveauId(Long niveauId){
        return etudiantRepository.countEtudiantsByNiveauId(niveauId);
    }


      public boolean existsByNomNiveauAndFiliere(String nomNiveau, Filiere filiere) {
        return niveauRepository.existsByNomNiveauAndFiliere(nomNiveau, filiere);
    }


    //EDT
      public List<Seance> getEmploiDuTempsByNiveauId(Long niveauId) {
        return niveauRepository.findSeancesByNiveauId(niveauId);
    }
}
