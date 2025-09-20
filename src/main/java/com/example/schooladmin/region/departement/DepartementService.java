package com.example.schooladmin.region.departement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schooladmin.region.Region;

import lombok.Data;

@Service
@Data
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    //Recuperer tous les departement
    public List<Departement>  getAllDepartements(){
        return departementRepository.findAll();
    }
    

    //Recuperer les departements par Region
    public List<Departement> getDepartementsByRegionId(Long regionId){
        return departementRepository.findByRegionId(regionId);
        
    }

    //recuperer les departements par Id

    public Departement getDepartementById(Long id){
        return departementRepository.findById(id).orElseThrow(()-> new RuntimeException("Departement non trouvé"));
    }

    //Ajouter un departement
    public Departement savDepartement(Departement departement){
        return departementRepository.save(departement);
    }


    //Supprimer un departement
    public void deleteDepartement(Long departementId){
        departementRepository.deleteById( departementId);
    }

    //Verification de l'existence 
    public boolean existsByNomDepartementAndRegionId(String nomDepartement,Long regionId){
        return departementRepository.existsByNomDepartementAndRegionId(nomDepartement,regionId);
    }
    
    public boolean existsByNomDepartementAndRegion(String nomDepartement,Region region){
        return departementRepository.existsByNomDepartementAndRegion(nomDepartement,region);
    }



}
