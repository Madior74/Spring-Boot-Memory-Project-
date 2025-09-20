package com.example.schooladmin.region.departement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.region.Region;
import com.example.schooladmin.region.RegionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/admin/departements")
public class DepartementController {

    @Autowired
    private DepartementService departementService;

    @Autowired
    private RegionService regionService;

    //Recuperation des departements
    @GetMapping
    public List<Departement> getAllDepartements(){
      return  departementService.getAllDepartements();
    }
   
    //recuperer les departements par region
    @GetMapping("/departement/{regionId}")
    public List<Departement> getDepartementsByRegionId(@PathVariable Long regionId){
        return departementService.getDepartementsByRegionId(regionId);
    }

    //Nouveau departement
    @PostMapping("/departement/{regionId}")
    public ResponseEntity<?> addDepartementToRegion(@PathVariable Long regionId,@RequestBody Departement departementDetails){
        //Verification de l'existence de la region
        Region region =regionService.findById(regionId);

        if (region ==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Region non trouvé");
        }
        //Verification si le departement existe dans cette region
        boolean  regionExiste= departementService.existsByNomDepartementAndRegion(departementDetails.getNomDepartement(),region);
        if (regionExiste) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ce Departement existe deja dans cette region");

            
        }

        //Creation du niveau
        departementDetails.setRegion(region);
        Departement saveDepartement =departementService.savDepartement(departementDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveDepartement);
    }
    


    //Supprimer un departement
    @DeleteMapping("/{id}")
    public void deleteDepartement(@PathVariable Long id){
        departementService.deleteDepartement(id);
    }


    //Existence
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkDepartementExist(
            @RequestParam String nomDepartement,
            @RequestParam Long regionId) {
        boolean exists = departementService.existsByNomDepartementAndRegionId(nomDepartement, regionId);
        return ResponseEntity.ok(exists);
    }

}
