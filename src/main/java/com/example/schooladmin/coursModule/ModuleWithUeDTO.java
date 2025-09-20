package com.example.schooladmin.coursModule;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ModuleWithUeDTO {
    private Long id;
    private String nomModule;
    private int volumeHoraire;
    private Double creditModule;
    private String nomUE; 
    
    // Constructor
    public ModuleWithUeDTO(Long id, String nomModule, int volumeHoraire, Double creditModule, String nomUE) {
        this.id = id;
        this.nomModule = nomModule;
        this.volumeHoraire = volumeHoraire;
        this.creditModule = creditModule;
        this.nomUE = nomUE;
    }
    
}