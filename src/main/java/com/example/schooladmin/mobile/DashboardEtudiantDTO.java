package com.example.schooladmin.mobile;

import java.util.List;

import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.seance.Seance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardEtudiantDTO {
    private List<Seance> mesSeances;
    private List<Seance> mesAbsences;
    private List<Seance> seancesProgrammeesNiveau;
    private List<Evaluation> evaluationsProgrammees;
}
