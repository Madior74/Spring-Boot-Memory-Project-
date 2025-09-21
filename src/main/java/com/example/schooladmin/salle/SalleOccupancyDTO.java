package com.example.schooladmin.salle;

import java.time.LocalDate;
import java.util.List;

import com.example.schooladmin.evaluation.dto.EvaluationDTO;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceDTO;



public class SalleOccupancyDTO {
    private Long salleId;
    private LocalDate date;
    private List<SeanceDTO> seances;       
    private List<EvaluationDTO> evaluations;
    


    public SalleOccupancyDTO() {
    }
    public SalleOccupancyDTO(Long salleId, LocalDate date, List<SeanceDTO> seances, List<EvaluationDTO> evaluations) {
        this.salleId = salleId;
        this.date = date;
        this.seances = seances;
        this.evaluations = evaluations;
    }
    public Long getSalleId() {
        return salleId;
    }
    public void setSalleId(Long salleId) {
        this.salleId = salleId;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public List<SeanceDTO> getSeances() {
        return seances;
    }
    public void setSeances(List<SeanceDTO> seances) {
        this.seances = seances;
    }
    public List<EvaluationDTO> getEvaluations() {
        return evaluations;
    }
    public void setEvaluations(List<EvaluationDTO> evaluations) {
        this.evaluations = evaluations;
    }
    // Removed duplicate setSeances method to resolve erasure conflict
 

    

}