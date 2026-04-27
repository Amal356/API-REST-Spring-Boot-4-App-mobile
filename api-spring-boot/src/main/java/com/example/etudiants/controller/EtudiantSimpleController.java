package com.example.etudiants.controller;

import com.example.etudiants.dto.EtudiantSimpleDTO;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.repository.EtudiantRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/etudiants-simple")
public class EtudiantSimpleController {

    private final EtudiantRepository etudiantRepository;

    public EtudiantSimpleController(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }

    @GetMapping
    public List<EtudiantSimpleDTO> getAll() {
        return etudiantRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EtudiantSimpleDTO convertToDTO(Etudiant etudiant) {
        EtudiantSimpleDTO dto = new EtudiantSimpleDTO();
        dto.setId(etudiant.getId());
        dto.setCin(etudiant.getCin());
        dto.setNom(etudiant.getNom());
        dto.setDateNaissance(etudiant.getDateNaissance());
        dto.setEmail(etudiant.getEmail());
        dto.setAnneePremiereInscription(etudiant.getAnneePremiereInscription());
        dto.setAge(calculateAge(etudiant.getDateNaissance()));
        
        if (etudiant.getDepartement() != null) {
            dto.setDepartementId(etudiant.getDepartement().getId());
            dto.setDepartementNom(etudiant.getDepartement().getNom());
        }
        
        return dto;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
