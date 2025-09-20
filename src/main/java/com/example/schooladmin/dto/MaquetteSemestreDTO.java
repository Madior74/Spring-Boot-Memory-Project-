package com.example.schooladmin.dto;

import lombok.Data;
import java.util.List;

import com.example.schooladmin.niveau.Niveau;

@Data
public class MaquetteSemestreDTO {
    private String nomSemestre;
    private Niveau niveau; 
    private String filiere;

   private double totalCredits;
    private double totalVolumeHoraire;
    private int nombreModules;

    private List<UEInfo> ues;

    @Data
    public static class UEInfo {
        private String nomUE;
        private String codeUE;
        private List<ModuleInfo> modules;
    }

    @Data
    public static class ModuleInfo {
        private String nomModule;
        private int volumeHoraire;
        private Double creditModule;
    }
}