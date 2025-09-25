package com.example.schooladmin.statistique;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schooladmin.etudiant.etudiant.EtudiantRepository;

@Service
public class StatistiqueService {
    @Autowired
    private EtudiantRepository etudiantRepository;

    public List<FiliereTendanceDTO> getTendanceParFiliere(Long anneeId) {
        return etudiantRepository.getTendanceParFiliere(anneeId);
    }
}