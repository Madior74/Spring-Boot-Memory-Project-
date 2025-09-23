package com.example.schooladmin.salle.mobile;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.salle.Salle;
import com.example.schooladmin.salle.SalleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/etudiants/salees")
public class EtudiantSalleController {

    private final SalleService salleService;


       @GetMapping("/statut-now")
    public List<Salle> getSallesAvecStatutNow() {
        return salleService.getAllSallesWithStatutNow();
    }
    
}
