package com.example.grading.service;

import com.example.grading.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    List<NoteDTO> getAll();
    NoteDTO getById(Long id);
    List<NoteDTO> getByStudentId(Long studentId);
    NoteDTO create(NoteDTO dto);
    NoteDTO update(Long id, NoteDTO dto);
    void delete(Long id);
}

