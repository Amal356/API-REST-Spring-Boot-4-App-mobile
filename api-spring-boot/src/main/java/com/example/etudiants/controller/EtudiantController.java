package com.example.etudiants.controller;

import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    private Map<String, Object> toMap(EtudiantDTO dto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", dto.getId());
        map.put("cin", dto.getCin());
        map.put("nom", dto.getNom());
        map.put("dateNaissance", dto.getDateNaissance());
        map.put("email", dto.getEmail());
        map.put("anneePremiereInscription", dto.getAnneePremiereInscription());
        map.put("age", dto.getAge());
        map.put("departementId", dto.getDepartementId());
        map.put("departementNom", dto.getDepartementNom());
        return map;
    }

    @GetMapping
    @Operation(summary = "Liste tous les étudiants")
    public List<Map<String, Object>> getAll(@RequestParam(required = false) Integer annee) {
        if (annee != null) {
            return etudiantService.findByAnnee(annee).stream()
                    .map(this::toMap)
                    .collect(Collectors.toList());
        }
        // Utiliser le service simple qui fonctionne déjà
        return etudiantService.getAllEtudiants().stream()
                .map(dto -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", dto.getId());
                    map.put("cin", dto.getCin());
                    map.put("nom", dto.getNom());
                    map.put("dateNaissance", dto.getDateNaissance());
                    map.put("email", dto.getEmail());
                    map.put("anneePremiereInscription", dto.getAnneePremiereInscription());
                    map.put("age", dto.getAge());
                    map.put("departementId", dto.getDepartementId());
                    map.put("departementNom", dto.getDepartementNom());
                    return map;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un étudiant")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        try {
            EtudiantDTO dto = etudiantService.getEtudiantById(id);
            return new ResponseEntity<>(toMap(dto), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Non trouvé"), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Operation(summary = "Crée un étudiant")
    @ApiResponse(responseCode = "201", description = "Etudiant créé")
    public ResponseEntity<Map<String, Object>> create(@RequestBody EtudiantDTO etudiantDTO) {
        try {
            // Validation des champs requis
            if (etudiantDTO.getCin() == null || etudiantDTO.getNom() == null ||
                etudiantDTO.getCin().trim().isEmpty() || etudiantDTO.getNom().trim().isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "CIN et Nom sont requis"), HttpStatus.BAD_REQUEST);
            }

            EtudiantDTO created = etudiantService.createEtudiant(etudiantDTO);
            return new ResponseEntity<>(toMap(created), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Erreur: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un étudiant")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id,
                                                     @RequestBody EtudiantDTO etudiantDTO) {
        try {
            // Validation des champs requis
            if (etudiantDTO.getCin() == null || etudiantDTO.getNom() == null ||
                etudiantDTO.getCin().trim().isEmpty() || etudiantDTO.getNom().trim().isEmpty()) {
                return new ResponseEntity<>(Map.of("error", "CIN et Nom sont requis"), HttpStatus.BAD_REQUEST);
            }

            EtudiantDTO updated = etudiantService.updateEtudiant(id, etudiantDTO);
            return new ResponseEntity<>(toMap(updated), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Erreur: " + e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un étudiant")
    @ApiResponse(responseCode = "204", description = "Etudiant supprimé")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }
}
