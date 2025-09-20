package com.example.schooladmin.utilisateur;

import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.schooladmin.admin.Admin;
import com.example.schooladmin.admin.AdminRepository;
import com.example.schooladmin.etudiant.candiddat.Candidat;
import com.example.schooladmin.etudiant.candiddat.CandidatRepository;
import com.example.schooladmin.professeur.Professeur;
import com.example.schooladmin.professeur.ProfesseurRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final ProfesseurRepository professeurRepository;
    private final CandidatRepository candidatPreInscritRepository;

    public CustomUserDetailsService(AdminRepository adminRepository,
                ProfesseurRepository professeurRepository,
                CandidatRepository candidatPreInscritRepository) {
            this.adminRepository = adminRepository;
            this.professeurRepository = professeurRepository;
        this.candidatPreInscritRepository = candidatPreInscritRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Chercher dans Admin
        Optional<Admin> adminOpt = adminRepository.findByEmail(email);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    admin.getEmail(),
                    admin.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        // Chercher dans Professeur
        Optional<Professeur> profOpt = professeurRepository.findByEmail(email);
        if (profOpt.isPresent()) {
            Professeur prof = profOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    prof.getEmail(),
                    prof.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_PROFESSEUR")));
        }

        // Chercher dans Etudiant (si besoin)
        Optional<Candidat> etuOpt = candidatPreInscritRepository.findByEmail(email);
        if (etuOpt.isPresent()) {
            Candidat etu = etuOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    etu.getEmail(),
                    etu.getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ETUDIANT")));
        }

        // Aucun utilisateur trouvé
        throw new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + email);
    }
}
