package com.example.etudiants.service;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.config.ResourceNotFoundException;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.mapper.EtudiantMapper;
import com.example.etudiants.repository.EtudiantRepository;
import com.example.etudiants.repository.DepartementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository etudiantRepository;
    private final DepartementRepository departementRepository;
    private final EtudiantMapper etudiantMapper;

    @Override
    @Cacheable(value = "etudiants")
    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public EtudiantDTO getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .map(etudiantMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found with id: " + id));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO createEtudiant(EtudiantDTO dto) {
        // Vérifier si le CIN existe déjà
        if (etudiantRepository.findByCin(dto.getCin()).isPresent()) {
            throw new RuntimeException("Un étudiant avec ce CIN existe déjà : " + dto.getCin());
        }

        Etudiant etudiant = etudiantMapper.toEntity(dto);
        if (dto.getDepartementId() != null) {
            Departement departement = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departement not found"));
            etudiant.setDepartement(departement);
        }
        return etudiantMapper.toDto(etudiantRepository.save(etudiant));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public EtudiantDTO updateEtudiant(Long id, EtudiantDTO dto) {
        Etudiant existing = etudiantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Etudiant not found"));
        
        // Vérifier si le nouveau CIN appartient à un autre étudiant
        etudiantRepository.findByCin(dto.getCin()).ifPresent(e -> {
            if (!e.getId().equals(id)) {
                throw new RuntimeException("Le CIN " + dto.getCin() + " est déjà utilisé par un autre étudiant.");
            }
        });

        existing.setCin(dto.getCin());
        existing.setNom(dto.getNom());
        existing.setDateNaissance(dto.getDateNaissance());
        existing.setEmail(dto.getEmail());
        existing.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        
        if (dto.getDepartementId() != null) {
            Departement departement = departementRepository.findById(dto.getDepartementId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departement not found"));
            existing.setDepartement(departement);
        } else {
            existing.setDepartement(null);
        }

        return etudiantMapper.toDto(etudiantRepository.save(existing));
    }

    @Override
    @CacheEvict(value = "etudiants", allEntries = true)
    public void deleteEtudiant(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Etudiant not found with id: " + id);
        }
        etudiantRepository.deleteById(id);
    }

    @Override
    public List<EtudiantDTO> findByAnnee(int annee) {
        return etudiantRepository.findByAnneePremiereInscription(annee).stream()
                .map(etudiantMapper::toDto)
                .collect(Collectors.toList());
    }
}
