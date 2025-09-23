package com.example.schooladmin.mobile;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.assiduite.Assiduite;
import com.example.schooladmin.assiduite.AssiduiteDTO;
import com.example.schooladmin.assiduite.AssiduiteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/etudiants/assiduites")
public class EtudiantAssiduiteController {

    private final AssiduiteService assiduiteService;

    // get

    @GetMapping("/mes-absences")
    public ResponseEntity<List<AssiduiteDTO>> getMesAssiduites(Authentication authentication) {
        String email = authentication.getName();
        List<Assiduite> asd = assiduiteService.getAssiduiteByEtudiantEmail(email);
        List<AssiduiteDTO> noteDTOs = asd.stream()
                .map(AssiduiteDTO::new)
                .toList();
        return ResponseEntity.ok(noteDTOs);
    }
}
