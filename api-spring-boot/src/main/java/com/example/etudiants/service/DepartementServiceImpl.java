package com.example.etudiants.service;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.mapper.DepartementMapper;
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

    @Override
    public List<DepartementDTO> getAllDepartements() {
        return departementRepository.findAll().stream()
                .map(DepartementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartementDTO getDepartementById(Long id) {
        return departementRepository.findById(id)
                .map(DepartementMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Departement not found"));
    }

    @Override
    public DepartementDTO createDepartement(DepartementDTO dto) {
        Departement departement = DepartementMapper.toEntity(dto);
        return DepartementMapper.toDto(departementRepository.save(departement));
    }

    @Override
    public DepartementDTO updateDepartement(Long id, DepartementDTO dto) {
        Departement existing = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departement not found"));
        existing.setNom(dto.getNom());
        return DepartementMapper.toDto(departementRepository.save(existing));
    }

    @Override
    public void deleteDepartement(Long id) {
        departementRepository.deleteById(id);
    }
}
