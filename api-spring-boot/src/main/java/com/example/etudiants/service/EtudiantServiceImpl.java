package com.example.etudiants.service;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.mapper.EtudiantMapper;
import com.example.etudiants.repository.EtudiantRepository;
import com.example.etudiants.repository.DepartementRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;

    public EtudiantServiceImpl(EtudiantRepository etudiantRepository,
                           DepartementRepository departementRepository) {
        this.etudiantRepository = etudiantRepository;
        this.departementRepository = departementRepository;
    }

    @Override
    @Cacheable(value = "etudiants")
    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
                .map(EtudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EtudiantDTO getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .map(EtudiantMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO createEtudiant(EtudiantDTO dto) {
        Etudiant etudiant = EtudiantMapper.toEntity(dto);
        if (dto.getDepartementId() != null) {
            Departement departement = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Departement not found"));
            etudiant.setDepartement(departement);
        }
        return EtudiantMapper.toDto(etudiantRepository.save(etudiant));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO updateEtudiant(Long id, EtudiantDTO dto) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Etudiant not found"));

        existing.setCin(dto.getCin());
        existing.setNom(dto.getNom());
        existing.setDateNaissance(dto.getDateNaissance());
        existing.setEmail(dto.getEmail());
        existing.setAnneePremiereInscription(dto.getAnneePremiereInscription());

        if (dto.getDepartementId() != null) {
            Departement departement = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Departement not found"));
            existing.setDepartement(departement);
        }

        return EtudiantMapper.toDto(etudiantRepository.save(existing));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public List<EtudiantDTO> findByAnnee(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee).stream()
                .map(EtudiantMapper::toDto)
                .collect(Collectors.toList());
    }
}
