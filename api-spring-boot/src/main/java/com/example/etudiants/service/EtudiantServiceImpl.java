package com.example.etudiants.service;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.entity.Departement;
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

    private EtudiantDTO toDto(Etudiant entity) {
        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(entity.getId());
        dto.setCin(entity.getCin());
        dto.setNom(entity.getNom());
        dto.setDateNaissance(entity.getDateNaissance());
        dto.setEmail(entity.getEmail());
        dto.setAnneePremiereInscription(entity.getAnneePremiereInscription());
        dto.setAge(entity.age());
        
        if (entity.getDepartement() != null) {
            dto.setDepartementId(entity.getDepartement().getId());
            dto.setDepartementNom(entity.getDepartement().getNom());
        }
        return dto;
    }

    private Etudiant toEntity(EtudiantDTO dto) {
        Etudiant entity = new Etudiant();
        entity.setCin(dto.getCin());
        entity.setNom(dto.getNom());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setEmail(dto.getEmail());
        entity.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        return entity;
    }

    @Override
    @Cacheable(value = "etudiants")
    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "etudiants", key = "#id")
    public EtudiantDTO getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Etudiant not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO createEtudiant(EtudiantDTO dto) {
        Etudiant etudiant = toEntity(dto);
        if (dto.getDepartementId() != null) {
            Departement departement = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Departement not found"));
            etudiant.setDepartement(departement);
        }
        return toDto(etudiantRepository.save(etudiant));
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

        return toDto(etudiantRepository.save(existing));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    @Override
    public List<EtudiantDTO> findByAnnee(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
