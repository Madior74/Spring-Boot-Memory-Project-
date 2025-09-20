package com.example.schooladmin.filiere;
import lombok.Data;

import java.util.List;

@Data
public class FiliereRequest {
    private String nomFiliere;
    private String description;
    private String creePar;
    private String modifiePar;
}
