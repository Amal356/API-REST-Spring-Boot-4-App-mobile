package com.example.grading.controller;

import com.example.grading.dto.NoteDTO;
import com.example.grading.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    @Operation(summary = "Lister toutes les notes")
    public List<NoteDTO> getAll(@RequestParam(required = false) Long studentId) {
        if (studentId != null) {
            return noteService.getByStudentId(studentId);
        }
        return noteService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une note par id")
    public ResponseEntity<NoteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une note")
    public ResponseEntity<NoteDTO> create(@Valid @RequestBody NoteDTO dto) {
        return new ResponseEntity<>(noteService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une note")
    public ResponseEntity<NoteDTO> update(@PathVariable Long id, @Valid @RequestBody NoteDTO dto) {
        return ResponseEntity.ok(noteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une note")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

