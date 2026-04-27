package com.example.grading.client;

import com.example.grading.dto.EtudiantRefDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "etudiants")
public interface EtudiantClient {
    @GetMapping("/api/etudiants/{id}")
    EtudiantRefDTO getEtudiantById(@PathVariable("id") Long id);
}

