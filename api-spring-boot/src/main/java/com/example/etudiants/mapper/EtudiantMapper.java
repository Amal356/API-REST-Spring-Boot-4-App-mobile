package com.example.etudiants.mapper;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Etudiant;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper {

    public EtudiantDTO toDto(Etudiant entity) {
        if (entity == null) return null;
        
        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(entity.getId());
        dto.setCin(entity.getCin());
        dto.setNom(entity.getNom());
        dto.setDateNaissance(entity.getDateNaissance());
        dto.setEmail(entity.getEmail());
        dto.setAnneePremiereInscription(entity.getAnneePremiereInscription());
        dto.setAge(entity.age());
        
        if (entity.getDepartement() != null) {
            dto.setDepartementId(entity.getDepartement().getId());
            dto.setDepartementNom(entity.getDepartement().getNom());
        }
        return dto;
    }

    public Etudiant toEntity(EtudiantDTO dto) {
        if (dto == null) return null;
        
        Etudiant entity = new Etudiant();
        entity.setId(dto.getId());
        entity.setCin(dto.getCin());
        entity.setNom(dto.getNom());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setEmail(dto.getEmail());
        entity.setAnneePremiereInscription(dto.getAnneePremiereInscription());
        return entity;
    }
}
