package com.example.etudiants.service;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.repository.DepartementRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;

    public DepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    private DepartementDTO toDto(Departement entity) {
        DepartementDTO dto = new DepartementDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        return dto;
    }

    private Departement toEntity(DepartementDTO dto) {
        Departement entity = new Departement();
        entity.setNom(dto.getNom());
        return entity;
    }

    @Override
    public List<DepartementDTO> getAllDepartements() {
        return departementRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartementDTO getDepartementById(Long id) {
        return departementRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Departement not found"));
    }

    @Override
    public DepartementDTO createDepartement(DepartementDTO dto) {
        Departement departement = toEntity(dto);
        return toDto(departementRepository.save(departement));
    }

    @Override
    public DepartementDTO updateDepartement(Long id, DepartementDTO dto) {
        Departement existing = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departement not found"));
        existing.setNom(dto.getNom());
        return toDto(departementRepository.save(existing));
    }

    @Override
    public void deleteDepartement(Long id) {
        departementRepository.deleteById(id);
    }
}
