package com.example.etudiants.service;

import com.example.etudiants.dto.DepartementDTO;
import java.util.List;

public interface DepartementService {
    List<DepartementDTO> getAllDepartements();
    DepartementDTO getDepartementById(Long id);
    DepartementDTO createDepartement(DepartementDTO dto);
    DepartementDTO updateDepartement(Long id, DepartementDTO dto);
    void deleteDepartement(Long id);
}
