package com.example.schooladmin.salle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;


    //get

    public List<Salle> getAllSalles(){
        return salleRepository.findAll();
    }


    //Create
    public Salle creatSalle(Salle salle){
        return salleRepository.save(salle);

    }


    //delete
    public void deleteSalle(Long id){
        salleRepository.deleteById(id);
    }


    //Existence
    public boolean existsByNomSalle(String nomSalle){
        return salleRepository.existsByNomSalle(nomSalle);
    }
    
}
