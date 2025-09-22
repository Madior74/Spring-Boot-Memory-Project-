package com.example.schooladmin.emplois_du_temps;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.schooladmin.niveau.NiveauRepository;
import com.example.schooladmin.seance.Seance;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmploisDuTempsService {
    private final NiveauRepository niveauRepository;    


    public List<EmploiDuTempsDTO> getEmploiDuTempsDTOByNiveauId(Long niveauId) {
    List<Seance> seances = niveauRepository.findSeancesByNiveauId(niveauId);
    return seances.stream()
                  .map(EmploiDuTempsDTO::new)
                  .collect(Collectors.toList());
}



//affichage hebdomadaire
public Map<LocalDate, List<EmploiDuTempsDTO>> getEmploiDuTempsGroupedByDay(Long niveauId) {
    List<EmploiDuTempsDTO> emploiDuTemps = getEmploiDuTempsDTOByNiveauId(niveauId);
    return emploiDuTemps.stream()
                        .collect(Collectors.groupingBy(EmploiDuTempsDTO::getDate));
}


    
}
