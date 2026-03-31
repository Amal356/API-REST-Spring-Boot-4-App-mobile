package com.example.etudiantsapi.controller;

import com.example.etudiantsapi.model.Etudiant;
import com.example.etudiantsapi.repository.EtudiantRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
@CrossOrigin(origins = "*")
public class EtudiantController {

    private final EtudiantRepository repo;

    public EtudiantController(EtudiantRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Etudiant> getAll() {
        return repo.findAll();
    }
}