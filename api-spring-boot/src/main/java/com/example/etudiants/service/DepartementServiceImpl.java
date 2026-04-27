package com.example.etudiants.service;

import com.example.etudiants.config.ResourceNotFoundException;
import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.mapper.DepartementMapper;
import com.example.etudiants.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartementServiceImpl implements DepartementService {

    private final DepartementRepository departementRepository;
    private final DepartementMapper departementMapper;

    @Override
    public List<DepartementDTO> getAllDepartements() {
        return departementRepository.findAll().stream()
                .map(departementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DepartementDTO getDepartementById(Long id) {
        return departementRepository.findById(id)
                .map(departementMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Departement not found"));
    }

    @Override
    public DepartementDTO createDepartement(DepartementDTO dto) {
        Departement departement = departementMapper.toEntity(dto);
        return departementMapper.toDto(departementRepository.save(departement));
    }

    @Override
    public DepartementDTO updateDepartement(Long id, DepartementDTO dto) {
        Departement existing = departementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departement not found"));
        existing.setNom(dto.getNom());
        return departementMapper.toDto(departementRepository.save(existing));
    }

    @Override
    public void deleteDepartement(Long id) {
        if (!departementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Departement not found");
        }
        departementRepository.deleteById(id);
    }
}
