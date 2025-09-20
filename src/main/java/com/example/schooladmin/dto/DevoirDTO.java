package com.example.schooladmin.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DevoirDTO {
    private Double note;
    private String dateAttribution; 
    private Long etudiantId;
    private Long professeurId;
    private Long moduleId;
}
