package com.example.schooladmin.etudiant.document;
import java.lang.StackWalker.Option;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface  DocumentRepository  extends JpaRepository<Document,Long>{

    List<Document> findByCandidat_Id(Long etudiantId);
    //Verification de l'existence d'un document par son nom et l'ID de l'étudiant
boolean existsByNomAndCandidat_Id(String nom, Long candidatId);

}
