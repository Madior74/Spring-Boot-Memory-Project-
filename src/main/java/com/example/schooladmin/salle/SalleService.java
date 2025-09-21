package com.example.schooladmin.salle;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.schooladmin.evaluation.EvaluationRepository;
import com.example.schooladmin.seance.SeanceRepository;

@Service
public class SalleService {

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private SeanceRepository seanceRepository;


  

       public List<Salle> getAllSalles() {
        List<Salle> salles = salleRepository.findAll();

        LocalDate aujourdHui = LocalDate.now();
        LocalTime maintenant = LocalTime.now();

        for (Salle salle : salles) {
            boolean occupeeParSeance = seanceRepository.existsBySalleAndDateSeanceAndHeures(
                    salle.getId(), aujourdHui, maintenant);

            boolean occupeeParEval = evaluationRepository.existsBySalleAndDateEvaluationAndHeures(
                    salle.getId(), aujourdHui, maintenant);

            salle.setOccupee(occupeeParSeance || occupeeParEval);
        }

        return salles;
    }


    //Create
    public Salle creatSalle(Salle salle){
        return salleRepository.save(salle);

    }


    //delete
    public void deleteSalle(Long id){
        salleRepository.deleteById(id);
    }


    //Existence
    public boolean existsByNomSalle(String nomSalle){
        return salleRepository.existsByNomSalle(nomSalle);
    }
    
}
