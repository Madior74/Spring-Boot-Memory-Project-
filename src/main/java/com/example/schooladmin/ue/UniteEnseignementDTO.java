package com.example.schooladmin.ue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UniteEnseignementDTO {

    private String nomUE;
    private String codeUe;
    private Long id;

    public UniteEnseignementDTO(UniteEnseignement ue) {
        this.id = ue.getId();
        this.nomUE = ue.getNomUE();
        this.codeUe = ue.getCodeUE();
    }
    
}
