package com.example.schooladmin.mobile;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.salle.Salle;
import com.example.schooladmin.salle.SalleService;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantDashboardController {

    private final EtudiantDashboardService dashboardService;


       @GetMapping("/assiduites/mes-absences")
    public ResponseEntity<List<SeanceDTO>> getMesAssiduites(Authentication authentication) {
        String email = authentication.getName();
        List<Seance> asd = dashboardService.getAssiduiteByEtudiantEmail(email);
        List<SeanceDTO> noteDTOs = asd.stream()
                .map(SeanceDTO::new)
                .toList();
        return ResponseEntity.ok(noteDTOs);
    }

      @GetMapping("/seances")
    public List<Seance> getSeancesByNiveau(Authentication authentication){
                String email = authentication.getName();
                return dashboardService.getSeanceByEtudiant(email);

    }



      @GetMapping("/evaluations")
    public List<Evaluation> getEvaluationProgrammeesByNiveau(Authentication authentication){
                String email = authentication.getName();
                return dashboardService.getEvaluationProgrammeesByNiveauId(email);

    }


    //Salles
     private final SalleService salleService;


       @GetMapping("/salles/statut-now")
    public List<Salle> getSallesAvecStatutNow() {
        return salleService.getAllSallesWithStatutNow();
    }


}
