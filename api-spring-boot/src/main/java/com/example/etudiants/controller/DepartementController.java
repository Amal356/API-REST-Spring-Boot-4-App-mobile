package com.example.etudiants.controller;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.service.DepartementService;
import io.swagger.v3.oas.annotations.Operation;
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
    public DepartementDTO getById(@PathVariable Long id) {
        return departementService.getDepartementById(id);
    }

    @PostMapping
    @Operation(summary = "Crée un département")
    public ResponseEntity<DepartementDTO> create(@RequestBody DepartementDTO dto) {
        return new ResponseEntity<>(departementService.createDepartement(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Met à jour un département")
    public DepartementDTO update(@PathVariable Long id, @RequestBody DepartementDTO dto) {
        return departementService.updateDepartement(id, dto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprime un département")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return ResponseEntity.noContent().build();
    }
}
