package com.example.etudiants.controller;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.service.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/departements")
public class DepartementController {

    private final DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @GetMapping
    @Operation(summary = "Liste tous les départements")
    public List<DepartementDTO> getAll() {
        return departementService.getAllDepartements();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupère un département")
    @ApiResponse(responseCode = "200", description = "Département trouvé")
    @ApiResponse(responseCode = "404", description = "Département non trouvé")
    public ResponseEntity<DepartementDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departementService.getDepartementById(id));
    }

    @PostMapping
    @Operation(summary = "Crée un département")
    @ApiResponse(responseCode = "201", description = "Département créé")
    public ResponseEntity<DepartementDTO> create(@Valid @RequestBody DepartementDTO dto) {
        return new ResponseEntity<>(departementService.createDepartement(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un département")
    @ApiResponse(responseCode = "200", description = "Département mis à jour")
    @ApiResponse(responseCode = "404", description = "Département non trouvé")
    public ResponseEntity<DepartementDTO> update(@PathVariable Long id, @Valid @RequestBody DepartementDTO dto) {
        return ResponseEntity.ok(departementService.updateDepartement(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un département")
    @ApiResponse(responseCode = "204", description = "Département supprimé")
    @ApiResponse(responseCode = "404", description = "Département non trouvé")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return ResponseEntity.noContent().build();
    }
}
