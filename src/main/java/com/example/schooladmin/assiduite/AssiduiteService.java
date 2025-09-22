package com.example.schooladmin.assiduite;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.schooladmin.anneeAcademique.AnneeAcademiqueService;
import com.example.schooladmin.note.Note;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class AssiduiteService {

    private final AssiduiteRepository assiduiteRepository;
    private final AnneeAcademiqueService anneeAcademiqueService;

    // ajout
    public Assiduite ajouterAssiduite(Assiduite assiduite) {
        if (assiduiteRepository.existsByEtudiant_IdAndSeanceIdAndStatutPresence(assiduite.getEtudiant().getId(),
                assiduite.getSeance().getId(), assiduite.getStatutPresence())) {
            throw new RuntimeException("Cet Etudiant a dèja une assiduité pour cette séance");

        }
        assiduite.setCreerPar(SecurityContextHolder.getContext().getAuthentication().getName());
        assiduite.setDatecreation(LocalDateTime.now());
        return assiduiteRepository.save(assiduite);
    }

     //Assiduite By Seance
     public List<Assiduite> getAssiduitesBySeance(Long seanceId) {
        return assiduiteRepository.findBySeanceId(seanceId);
    }

    

    // Get assiduite by id
    public Optional<Assiduite> getAssiduiteById(final Long id) {
        return assiduiteRepository.findById(id);
    }

    // Get All Assiduite
    public List<Assiduite> getAllAssiduites() {
        return assiduiteRepository.findAll();
    }


    //Mettre a jour
    public Assiduite updateAssiduite(long id, AssiduiteDTO assiduiteDTO) {
        Optional<Assiduite> existingAssiduite = assiduiteRepository.findById(id);
        if (existingAssiduite.isPresent()) {
            Assiduite updateAssiduite = existingAssiduite.get();
            updateAssiduite.setStatutPresence(assiduiteDTO.getStatutPresence());
            updateAssiduite.setDatemodification(LocalDateTime.now());
            updateAssiduite.setModifierPar(SecurityContextHolder.getContext().getAuthentication().getName());

        Assiduite savedAssiduite = assiduiteRepository.save(updateAssiduite);

        return savedAssiduite;
        } else {
            throw new RuntimeException("Assiduite not found with id: " + id);
        }
    }



    // Supprimer une Assiduite
    public void deleteAssiduite(final long id) {
        assiduiteRepository.deleteById(id);
    }


    //Assiduite par etudiant
      public List<Assiduite> getAssiduiteByEtudiantEmail(String email) {
        return assiduiteRepository.findByEtudiantDossierAdmissionCandidatEmail(email);
    }


}
