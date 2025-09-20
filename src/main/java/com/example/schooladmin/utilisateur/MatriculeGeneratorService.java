// package com.example.schooladmin.utilisateur;

// import com.example.schooladmin.anneeAcademique.AnneeAcademique;
// import com.example.schooladmin.etudiant.etudiant.EtudiantRepository;
// import com.example.schooladmin.filiere.Filiere;


// @Service
// public class MatriculeGeneratorService {

//       private EtudiantRepository etudiantRepository;

//     public String genererMatricule(Filiere filiere, AnneeAcademique annee) {
//         // Compter combien d'étudiants existent déjà dans cette filière + année
//         long count = etudiantRepository.countByFiliereAndAnneeAcademique(filiere, annee);

//         // Exemple de format : 2025-SI-001
//         String prefix = annee.getAnnee().substring(0, 4); // ex: "2025"
//         String codeFiliere = filiere.getCode(); // ex: "SI"

//         String numero = String.format("%03d", count + 1); // ex: "001"

//         return prefix + "-" + codeFiliere + "-" + numero;
//     }
    
// }
