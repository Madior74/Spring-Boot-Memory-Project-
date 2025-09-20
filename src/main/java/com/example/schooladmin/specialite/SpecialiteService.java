package com.example.schooladmin.specialite;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import lombok.Data;

@Service
@Data
public class SpecialiteService {


    @Autowired
    private SpecialiteRepository specialiteRepository;

    //Ajouter une Specialite
    public Specialite ajouterSpecialite(Specialite speci){
        return specialiteRepository.save(speci);
    }

    //recuperer par Id
    public Optional<Specialite> getSpecialiteById(final Long id){
        return specialiteRepository.findById(id);
    }


    ///recuper tous les diplomes
    public List<Specialite> getAllSpecialite(){
        return specialiteRepository.findAll();
    }


    //Recuper diplome par id
    public Specialite getDiplomeById(Long id){
        return specialiteRepository.findById(id).orElseThrow(()-> new RuntimeException("Diplome non Trouve"));
    }

    //Supprimer 
    public void deleteSpecialite(Long id){
        specialiteRepository.deleteById(id);
    }



  
    
}
