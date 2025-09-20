package com.example.schooladmin.etudiant.candiddat;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CandidatDTO {
    private Long id;
   private String nom;
   private String prenom;
   private String email;
   private Long filiereId;
   private Long niveauId;
   private Long anneeAcademiqueId;

   public CandidatDTO(Candidat candidat){
       this.id=candidat.getId();
       this.nom=candidat.getNom();
       this.prenom=candidat.getPrenom();
         this.email=candidat.getEmail();
       this.filiereId=candidat.getFiliereSouhaitee() != null ? candidat.getFiliereSouhaitee().getId() : null;
       this.niveauId=candidat.getNiveauSouhaite() != null ? candidat.getNiveauSouhaite().getId() : null;
       this.anneeAcademiqueId = candidat.getAnneeAcademique() != null ? candidat.getAnneeAcademique().getId() : null;
   }
    

    
}
