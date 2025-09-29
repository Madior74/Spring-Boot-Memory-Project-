package com.example.schooladmin.annonce;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.schooladmin.niveau.Niveau;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {
    List<Annonce> findByEstActiveTrueOrderByDatePublicationDesc();
    List<Annonce> findByNiveauAndEstActiveTrueOrderByDatePublicationDesc(Niveau niveau);
    List<Annonce> findByAnnonceGeneraleTrueAndEstActiveTrueOrderByDatePublicationDesc();
    List<Annonce> findByDateExpirationBefore(LocalDate date);
} 