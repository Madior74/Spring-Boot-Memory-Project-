package com.example.schooladmin.emplois_du_temps;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/edt")
public class EmploiDuTempsController {

    private final EmploiDuTempsService emploiDuTempsService;

    public EmploiDuTempsController(EmploiDuTempsService emploiDuTempsService) {
        this.emploiDuTempsService = emploiDuTempsService;
    }

    /**
     * Récupérer l’emploi du temps d’un niveau entre deux dates
     */
    @GetMapping("/semaine")
    public ResponseEntity<List<EvenementEmploiDuTemps>> getEmploiDuTemps(
            @RequestParam Long niveauId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        List<EvenementEmploiDuTemps> edt = emploiDuTempsService.genererEmploiDuTemps(niveauId, debut, fin);
        return ResponseEntity.ok(edt);
    }
}
