package com.example.schooladmin.evaluation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationRepository  extends JpaRepository<Evaluation,Long>{

    List<Evaluation> findByModuleId(Long moduleId);

    boolean existsByModuleIdAndDateEvaluation(Long moduleId, LocalDate dateEvaluation);

}
