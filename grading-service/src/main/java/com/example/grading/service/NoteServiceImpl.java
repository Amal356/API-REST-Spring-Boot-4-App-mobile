package com.example.grading.service;

import com.example.grading.client.EtudiantClient;
import com.example.grading.dto.NoteDTO;
import com.example.grading.entity.Note;
import com.example.grading.repository.NoteRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final EtudiantClient etudiantClient;

    @Override
    public List<NoteDTO> getAll() {
        return noteRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public NoteDTO getById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note introuvable id=" + id));
        return toDto(note);
    }

    @Override
    public List<NoteDTO> getByStudentId(Long studentId) {
        return noteRepository.findByStudentId(studentId).stream().map(this::toDto).toList();
    }

    @Override
    public NoteDTO create(NoteDTO dto) {
        validateStudentExists(dto.getStudentId());
        Note note = Note.builder()
                .studentId(dto.getStudentId())
                .matiere(dto.getMatiere())
                .valeur(dto.getValeur())
                .build();
        return toDto(noteRepository.save(note));
    }

    @Override
    public NoteDTO update(Long id, NoteDTO dto) {
        validateStudentExists(dto.getStudentId());
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note introuvable id=" + id));
        existing.setStudentId(dto.getStudentId());
        existing.setMatiere(dto.getMatiere());
        existing.setValeur(dto.getValeur());
        return toDto(noteRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new IllegalArgumentException("Note introuvable id=" + id);
        }
        noteRepository.deleteById(id);
    }

    private void validateStudentExists(Long studentId) {
        try {
            etudiantClient.getEtudiantById(studentId);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Etudiant introuvable id=" + studentId);
        }
    }

    private NoteDTO toDto(Note note) {
        return NoteDTO.builder()
                .id(note.getId())
                .studentId(note.getStudentId())
                .matiere(note.getMatiere())
                .valeur(note.getValeur())
                .build();
    }
}

