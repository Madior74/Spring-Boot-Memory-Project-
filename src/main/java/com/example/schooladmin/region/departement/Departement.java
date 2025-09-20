package com.example.schooladmin.region.departement;



import com.example.schooladmin.region.Region;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomDepartement;

    // @JsonIgnoreProperties({"departements","region"}) // Spécifiez la propriété à ignorer
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    public Departement(String nomDepartement, Region region) {
        this.nomDepartement = nomDepartement;
        this.region = region;
    }
}
