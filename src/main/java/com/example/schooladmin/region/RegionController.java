package com.example.schooladmin.region;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin/regions")
public class RegionController {


    @Autowired
    private RegionService regionService;
    

    //Recuperer tous les Regions
    @GetMapping
    public List<Region> getAllRegions(){
        return regionService.getAllRegions();
    }
    

    //Ajouter un Region
    @PostMapping("/save")
    public Region nouveauRegion(@RequestBody Region region){
        return regionService.saveRegion(region);
    }



    //Mettre a jour 



    //Supprimer une Region
    @DeleteMapping("/{id}")
    public void deleteRegion(@PathVariable Long id){
        regionService.deleteRegion(id);
    }
    
}


