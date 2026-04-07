package com.example.etudiants.controller;

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
    public List<Map<String, Object>> getAll() {
        return etudiantRepository.findAll().stream()
                .map(this::convertToMap)
                .collect(Collectors.toList());
    }

    private Map<String, Object> convertToMap(Etudiant etudiant) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", etudiant.getId());
        map.put("cin", etudiant.getCin());
        map.put("nom", etudiant.getNom());
        map.put("dateNaissance", etudiant.getDateNaissance());
        map.put("email", etudiant.getEmail());
        map.put("anneePremiereInscription", etudiant.getAnneePremiereInscription());
        map.put("age", calculateAge(etudiant.getDateNaissance()));
        
        if (etudiant.getDepartement() != null) {
            map.put("departementId", etudiant.getDepartement().getId());
            map.put("departementNom", etudiant.getDepartement().getNom());
        } else {
            map.put("departementId", null);
            map.put("departementNom", null);
        }
        
        return map;
    }

    private int calculateAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
