package com.example.etudiants.service;

import com.example.etudiants.dto.EtudiantDTO;
import java.util.List;

public interface EtudiantService {
    List<EtudiantDTO> getAllEtudiants();
    EtudiantDTO getEtudiantById(Long id);
    EtudiantDTO createEtudiant(EtudiantDTO dto);
    EtudiantDTO updateEtudiant(Long id, EtudiantDTO dto);
    void deleteEtudiant(Long id);
    List<EtudiantDTO> findByAnnee(int annee);
}
