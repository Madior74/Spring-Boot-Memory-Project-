package com.example.schooladmin.initializer;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import com.example.schooladmin.admin.AdminService;
import com.example.schooladmin.admin.RegisterAdminDTO;
import com.example.schooladmin.region.Region;
import com.example.schooladmin.region.RegionRepository;
import com.example.schooladmin.region.departement.Departement;
import com.example.schooladmin.region.departement.DepartementRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RegionRepository regionRepository;
    private final DepartementRepository departementRepository;

    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        if (regionRepository.count() == 0) {
            // Création des régions du Sénégal
            Region dakar = new Region("Dakar");
            Region thies = new Region("Thiès");
            Region saintLouis = new Region("Saint-Louis");
            Region ziguinchor = new Region("Ziguinchor");
            Region kaolack = new Region("Kaolack");

            regionRepository.saveAll(List.of(dakar, thies, saintLouis, ziguinchor, kaolack));

            // Création des départements liés aux régions
            Departement dakarDep = new Departement("Dakar", dakar);
            Departement guediawaye = new Departement("Guédiawaye", dakar);
            Departement pikine = new Departement("Pikine", dakar);
            Departement rufisque = new Departement("Rufisque", dakar);

            Departement thiesDep = new Departement("Thiès", thies);
            Departement mbour = new Departement("Mbour", thies);
            Departement tifess = new Departement("Tivaouane", thies);

            Departement saintLouisDep = new Departement("Saint-Louis", saintLouis);
            Departement dagana = new Departement("Dagana", saintLouis);
            Departement podor = new Departement("Podor", saintLouis);

            Departement ziguinchorDep = new Departement("Ziguinchor", ziguinchor);
            Departement bignona = new Departement("Bignona", ziguinchor);
            Departement oussouye = new Departement("Oussouye", ziguinchor);

            Departement kaolackDep = new Departement("Kaolack", kaolack);
            Departement nioro = new Departement("Nioro du Rip", kaolack);
            Departement guinguineo = new Departement("Guinguinéo", kaolack);

            departementRepository.saveAll(List.of(
                    dakarDep, guediawaye, pikine, rufisque,
                    thiesDep, mbour, tifess,
                    saintLouisDep, dagana, podor,
                    ziguinchorDep, bignona, oussouye,
                    kaolackDep, nioro, guinguineo));
        }



        if (adminService.getAllAdmins().isEmpty()) {
            // Création d'un administrateur par défaut
            RegisterAdminDTO adminDto = new RegisterAdminDTO();
            adminDto.setNom("Ndiaye");
            adminDto.setPrenom("Mamadou");
            adminDto.setAdresse("Pikine");
            adminDto.setPaysDeNaissance("Sénégal");
            adminDto.setDateDeNaissance(LocalDate.of(1990, 1, 1));
            adminDto.setCni("123456789");
            adminDto.setIne("987654321");
            adminDto.setTelephone("+221123456789");
            adminDto.setSexe("Masculin");
            adminDto.setEmail("admin@gmail.com");
            adminDto.setPassword("password123");
            adminDto.setRegionId(1L);
            adminDto.setDepartementId(1L);

            adminService.registerAdmin(adminDto);
            System.out.println("Administrateur par défaut créé avec succès.");
        }
    }
}

