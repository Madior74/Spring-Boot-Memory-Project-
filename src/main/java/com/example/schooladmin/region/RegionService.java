package com.example.schooladmin.region;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegionService {


    @Autowired
    private RegionRepository regionRepository;



    //Nouveau Region
    public Region saveRegion(Region region){
        return regionRepository.save(region);
    }


    //Recuperer tous les Regions
    public List<Region> getAllRegions(){
        return regionRepository.findAll();
    }
    

    //Mise a jour d'un Region
    public Region updateRegion(Region region){
        return regionRepository.save(region);
    }


    //Supprimer un region
    public void deleteRegion(Long id){
        regionRepository.deleteById(id);
    }


    //

    public Region findById(Long id){
        return regionRepository.findById(id).orElse(null);
    }



}
