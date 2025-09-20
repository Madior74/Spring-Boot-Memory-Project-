package com.example.schooladmin.note;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notes")
public class NoteController {

    private final NoteService noteService;
    private final NoteRepository noteRepository;

    // get All notes of a module with dto
    @GetMapping("/evaluation/{id}")
    public ResponseEntity<List<NoteResponse>> getNoteByEvaluation(@PathVariable Long id) {
        List<Note> notes=noteRepository.findByEvaluationId(id);

        List<NoteResponse> dto=notes.stream().map(NoteResponse::new).collect(Collectors.toList());
        return  ResponseEntity.ok(dto);

    }

    // nouvelle Note
    @PostMapping("/save")
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteCreationDTO dto) {

        Note note = noteService.createNote(dto);
        NoteDTO noteDTO = new NoteDTO(note); // Assuming NoteDTO has a constructor that accepts Note
        return ResponseEntity.status(HttpStatus.CREATED).body(noteDTO);
    }

    // get Note by id
    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // get Notes by etudiant
    @GetMapping("/etudiant/{id}")
    public List<Note> getNotesByEtudiantId(@PathVariable Long id) {
        return noteService.getNotesByEtudiantId(id);
    }

    //update Note
    @PutMapping("/update/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        Optional<Note> existingNoteOpt = noteRepository.findById(id);
        if (existingNoteOpt.isPresent()) {
            Note existingNote = existingNoteOpt.get();
            existingNote.setValeur(updatedNote.getValeur());
            // Mettre à jour d'autres champs si nécessaire
            noteRepository.save(existingNote);
            NoteDTO noteDTO = new NoteDTO(existingNote);
            return ResponseEntity.ok(noteDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // get Notes by etudiant ordered by date desc
    @GetMapping("/etudiant/{id}/recent")
    public List<Note> getNotesByEtudiantIdOrderByDateDesc(@PathVariable Long id) {
        return noteService.getNotesByEtudiantIdOrderByDateDesc(id);
    }

    // supprimer une note
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {

        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }


      // Existence check
    @GetMapping("/exists")
    public ResponseEntity<Boolean> noteExists(@RequestParam Long etudiantId,
            @RequestParam Long evaluationId) {
        boolean exists = noteService.noteExists(etudiantId, evaluationId);
        return ResponseEntity.ok(exists);
    }

}
