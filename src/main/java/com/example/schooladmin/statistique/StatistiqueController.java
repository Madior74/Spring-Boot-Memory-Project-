package com.example.schooladmin.statistique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/stats")
public class StatistiqueController {
    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/inscriptions-par-filiere")
    public List<FiliereTendanceDTO> getTendanceParFiliere(@RequestParam Long anneeId) {
        return statistiqueService.getTendanceParFiliere(anneeId);
    }
}