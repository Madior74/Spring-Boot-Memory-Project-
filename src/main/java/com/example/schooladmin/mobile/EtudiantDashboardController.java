package com.example.schooladmin.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.coursModule.CourseModule;
import com.example.schooladmin.coursModule.ModuleService;
import com.example.schooladmin.evaluation.Evaluation;
import com.example.schooladmin.evaluation.dto.EvaluationDTO;
import com.example.schooladmin.note.NoteDTO;
import com.example.schooladmin.salle.Salle;
import com.example.schooladmin.salle.SalleService;
import com.example.schooladmin.salle.SalleStatutDTO;
import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.seance.SeanceDTO;
import com.example.schooladmin.semestre.Semestre;
import com.example.schooladmin.semestre.SemestreDTO;
import com.example.schooladmin.ue.UniteEnseignement;
import com.example.schooladmin.ue.UniteEnseignementService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/etudiants")
@RequiredArgsConstructor
public class EtudiantDashboardController {

  private final EtudiantDashboardService dashboardService;
  private final UniteEnseignementService ueService;
  private final ModuleService moduleService;

  @GetMapping("/assiduites/mes-absences")
  public ResponseEntity<List<SeanceDTO>> getMesAssiduites(Authentication authentication) {
    String email = authentication.getName();
    List<Seance> asd = dashboardService.getAssiduiteByEtudiantEmail(email);
    List<SeanceDTO> noteDTOs = asd.stream()
        .map(SeanceDTO::new)
        .toList();
    return ResponseEntity.ok(noteDTOs);
  }

  @GetMapping("/seances")
  public List<SeanceDTO> getSeancesByNiveau(Authentication authentication) {
    String email = authentication.getName();
    List<Seance> seances = dashboardService.getSeanceByEtudiant(email);
    return seances.stream()
        .map(SeanceDTO::new)
        .toList();
  }

  @GetMapping("/evaluations")
  public ResponseEntity<List<EvaluationDTO>> getEvaluationProgrammeesByNiveau(Authentication authentication) {
    String email = authentication.getName();
    List<Evaluation> evs = dashboardService.getEvaluationProgrammeesByNiveauId(email);
    List<EvaluationDTO> dto = evs.stream()
        .map(EvaluationDTO::new)
        .toList();
    return ResponseEntity.ok(dto);
  }

  // Salles
  private final SalleService salleService;

  @GetMapping("/salles/statut-now")
  public List<Salle> getSallesAvecStatutNow() {
    return salleService.getAllSallesWithStatutNow();
  }

        @GetMapping("/statut-detaille")
    public List<SalleStatutDTO> getSallesStatutDetaille() {
        return salleService.getAllSallesWithStatutDetaille();
    }
    
  
  //Semestre Etudiant
  @GetMapping("/semestres")
  public ResponseEntity<List<SemestreDTO>> getSemestresByEtudiant(Authentication authentication) {
    String email = authentication.getName();
    List<Semestre> semestres = dashboardService.getSemestresByEtudiant(email);
    List<SemestreDTO> dto = semestres.stream()
    .map(SemestreDTO::new)
        .toList();
    return ResponseEntity.ok(dto);
  }

  //UE
      @GetMapping("/semestre/{semestreId}")
    public List<UniteEnseignement> getUEsBySemestre(@PathVariable Long semestreId) {
        return ueService.getUEBySemestre(semestreId);
    }


    //Modules By UE
        //get Module By UE
    @GetMapping("/ue/{ueId}")
    public ResponseEntity<Map<String, Object>> getModulesByUe(@PathVariable Long ueId) {
        Map<String, Object> response = new HashMap<>();
    
        // Récupérer les modules associés à l'UE
        List<CourseModule> modules = moduleService.getCourseModulesByUe(ueId);
    
        // Vérifier si des modules ont été trouvés
        if (modules == null || modules.isEmpty()) {
            response.put("status", "ERROR");
            response.put("message", "Aucun module trouvé pour cette UE");
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    
        // Réponse en cas de succès
        response.put("status", "SUCCESS");
        response.put("message", "Modules récupérés avec succès");
        response.put("data", modules);
    
        return ResponseEntity.ok(response);
    }

    //Notes By Etudiant
    @GetMapping("/mes-notes")
    public ResponseEntity<List<NoteDTO>> getNotesByEtudiant(Authentication authentication) {
        String email = authentication.getName();
        List<com.example.schooladmin.note.Note> notes = dashboardService.getNotesByEtudiantEmail(email);
        List<com.example.schooladmin.note.NoteDTO> dto = notes.stream().map(NoteDTO::new).toList();
        return ResponseEntity.ok(dto);
    }
  
  
  }