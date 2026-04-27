package com.example.etudiants.controller;

import com.example.etudiants.config.ResourceNotFoundException;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @GetMapping
    @Operation(summary = "Liste tous les étudiants")
    public List<EtudiantDTO> getAll(@RequestParam(required = false) Integer annee) {
        if (annee != null) {
            return etudiantService.findByAnnee(annee);
        }
        return etudiantService.getAllEtudiants();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un étudiant")
    @ApiResponse(responseCode = "200", description = "Etudiant trouvé")
    @ApiResponse(responseCode = "404", description = "Etudiant non trouvé")
    public ResponseEntity<EtudiantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.getEtudiantById(id));
    }

    @PostMapping
    @Operation(summary = "Crée un étudiant")
    @ApiResponse(responseCode = "201", description = "Etudiant créé")
    public ResponseEntity<EtudiantDTO> create(@Valid @RequestBody EtudiantDTO etudiantDTO) {
        // Validation des champs requis
        if (etudiantDTO.getCin() == null || etudiantDTO.getNom() == null ||
            etudiantDTO.getCin().trim().isEmpty() || etudiantDTO.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("CIN et Nom sont requis");
        }

        EtudiantDTO created = etudiantService.createEtudiant(etudiantDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un étudiant")
    @ApiResponse(responseCode = "200", description = "Etudiant mis à jour")
    @ApiResponse(responseCode = "404", description = "Etudiant non trouvé")
    public ResponseEntity<EtudiantDTO> update(@PathVariable Long id,
                                                 @Valid @RequestBody EtudiantDTO etudiantDTO) {
        // Validation des champs requis
        if (etudiantDTO.getCin() == null || etudiantDTO.getNom() == null ||
            etudiantDTO.getCin().trim().isEmpty() || etudiantDTO.getNom().trim().isEmpty()) {
            throw new IllegalArgumentException("CIN et Nom sont requis");
        }

        EtudiantDTO updated = etudiantService.updateEtudiant(id, etudiantDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un étudiant")
    @ApiResponse(responseCode = "204", description = "Etudiant supprimé")
    @ApiResponse(responseCode = "404", description = "Etudiant non trouvé")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }
}
