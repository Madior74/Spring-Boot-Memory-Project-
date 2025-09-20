package com.example.schooladmin.admin;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.schooladmin.enums.Role;
import com.example.schooladmin.region.Region;
import com.example.schooladmin.region.departement.Departement;

import jakarta.transaction.Transactional;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
public Admin registerAdmin(RegisterAdminDTO dto) {
    if (adminRepository.existsByEmail(dto.getEmail())) {
        throw new RuntimeException("Email déjà utilisé");
    }
    if (adminRepository.existsByCni(dto.getCni())) {
        throw new RuntimeException("CNI déjà utilisé");
    }

    Admin admin = new Admin();
    admin.setNom(dto.getNom());
    admin.setPrenom(dto.getPrenom());
    admin.setAdresse(dto.getAdresse());
    admin.setPaysDeNaissance(dto.getPaysDeNaissance());
    admin.setDateDeNaissance(dto.getDateDeNaissance());
    admin.setCni(dto.getCni());
    admin.setIne(dto.getIne());
    admin.setTelephone(dto.getTelephone());
    admin.setSexe(dto.getSexe());
    admin.setEmail(dto.getEmail());
    admin.setPassword(passwordEncoder.encode(dto.getPassword()));
    admin.setRole(Role.ROLE_ADMIN); // On force le rôle ici
    admin.setDateAjout(LocalDateTime.now());

    Region region = new Region();
    region.setId(dto.getRegionId());
    admin.setRegion(region);

    Departement departement = new Departement();
    departement.setId(dto.getDepartementId());
    admin.setDepartement(departement);

    return adminRepository.save(admin);
}


//Recuperer tous les admins
@Transactional
public List<Admin> getAllAdmins() {
    return adminRepository.findAll();
}
}