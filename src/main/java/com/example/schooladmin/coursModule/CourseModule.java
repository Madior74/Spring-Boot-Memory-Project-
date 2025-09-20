package com.example.schooladmin.coursModule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.schooladmin.seance.Seance;
import com.example.schooladmin.ue.UniteEnseignement;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;



import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "module")
@Data 
@NoArgsConstructor
@AllArgsConstructor
public class CourseModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_module", nullable = false)
    private String nomModule;

    @Column(name = "volume_horaire")
    private int volumeHoraire; // volume horaire total prévu

    @Column(name = "credit_module")
    private Double creditModule;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @Column(name = "date_ajout")
    private LocalDateTime dateAjout;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ue_id")
    @JsonBackReference
    private UniteEnseignement ue;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private List<Seance> seances = new ArrayList<>();

  

    public int getVolumeHoraireProgramme() {
        return seances.stream()
                .filter(s -> !s.isEstAnnulee())
                .filter(s -> s.isProgrammee())
                .mapToInt(Seance::getDureeEnHeures)
                .sum();
    }


    public int getVolumeHoraireDeroule() {
    return seances.stream()
            .filter(s -> !s.isEstAnnulee()) // Ne pas compter les séances annulées
            .mapToInt(Seance::getDureeEnHeures)
            .sum();
}
}
