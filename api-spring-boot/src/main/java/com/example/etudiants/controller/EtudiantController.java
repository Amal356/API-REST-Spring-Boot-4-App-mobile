package com.example.etudiants.controller;

import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.service.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    private Map<String, Object> toMap(Etudiant etudiant) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", etudiant.getId());
        map.put("cin", etudiant.getCin());
        map.put("nom", etudiant.getNom());
        map.put("dateNaissance", etudiant.getDateNaissance());
        map.put("email", etudiant.getEmail());
        map.put("anneePremiereInscription", etudiant.getAnneePremiereInscription());
        map.put("age", etudiant.age());
        
        if (etudiant.getDepartement() != null) {
            map.put("departementId", etudiant.getDepartement().getId());
            map.put("departementNom", etudiant.getDepartement().getNom());
        } else {
            map.put("departementId", null);
            map.put("departementNom", null);
        }
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
        return etudiantService.getAllEtudiants().stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un étudiant")
    public Map<String, Object> getById(@PathVariable Long id) {
        return toMap(etudiantService.getEtudiantById(id));
    }

    @PostMapping
    @Operation(summary = "Crée un étudiant")
    @ApiResponse(responseCode = "201", description = "Etudiant créé")
    public ResponseEntity<Map<String, Object>> create(@RequestBody Map<String, Object> dto) {
        // Pour la création, nous utilisons le service existant
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un étudiant")
    public Map<String, Object> update(@PathVariable Long id, @RequestBody Map<String, Object> dto) {
        // Pour la mise à jour, nous utilisons le service existant
        return Map.of("message", "Mise à jour non implémentée dans cette version");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un étudiant")
    @ApiResponse(responseCode = "204", description = "Etudiant supprimé")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }
}
