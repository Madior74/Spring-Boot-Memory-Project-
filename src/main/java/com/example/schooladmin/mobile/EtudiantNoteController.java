package com.example.schooladmin.mobile;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.schooladmin.note.Note;
import com.example.schooladmin.note.NoteDTO;
import com.example.schooladmin.note.NoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/etudiant/notes")
public class EtudiantNoteController {

    private final NoteService noteService;

 @GetMapping("/mes-notes")
    public ResponseEntity<List<NoteDTO>> getMesNotes(Authentication authentication) {
        String email = authentication.getName(); 

        List<Note> notes = noteService.getNotesByEtudiantEmail(email);

        List<NoteDTO> noteDTOs = notes.stream()
                                      .map(NoteDTO::new)
                                      .toList();

        return ResponseEntity.ok(noteDTOs);
    }

}
