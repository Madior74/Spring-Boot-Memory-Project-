package com.example.schooladmin.mobile;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.assiduite.Assiduite;
import com.example.schooladmin.assiduite.AssiduiteDTO;
import com.example.schooladmin.assiduite.AssiduiteService;
import com.example.schooladmin.seance.Seance;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantDashboardController {

    private final EtudiantDashboardService dashboardService;
        private final AssiduiteService assiduiteService;


    // @GetMapping
    // public DashboardEtudiantDTO getDashboard(Authentication authentication) {
    //     // Email de l’étudiant connecté via Spring Security
    //     String email = authentication.getName();
    //     return dashboardService.getDashboardByEmail(email);
    // }


       @GetMapping("/assiduites/mes-absences")
    public ResponseEntity<List<AssiduiteDTO>> getMesAssiduites(Authentication authentication) {
        String email = authentication.getName();
        List<Assiduite> asd = assiduiteService.getAssiduiteByEtudiantEmail(email);
        List<AssiduiteDTO> noteDTOs = asd.stream()
                .map(AssiduiteDTO::new)
                .toList();
        return ResponseEntity.ok(noteDTOs);
    }

      @GetMapping("/seances")
    public List<Seance> getSeancesByNiveau(Authentication authentication){
                String email = authentication.getName();
                return dashboardService.getSeanceByEtudiant(email);

    }



      @GetMapping("/evaluations")
    public List<Seance> getEvaluationByNiveau(Authentication authentication){
                String email = authentication.getName();
                return dashboardService.getSeanceByEtudiant(email);

    }



}
