package com.example.etudiants.mapper;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;
import org.springframework.stereotype.Component;

@Component
public class DepartementMapper {

    public DepartementDTO toDto(Departement entity) {
        if (entity == null) return null;
        return new DepartementDTO(entity.getId(), entity.getNom());
    }

    public Departement toEntity(DepartementDTO dto) {
        if (dto == null) return null;
        Departement entity = new Departement();
        entity.setId(dto.getId());
        entity.setNom(dto.getNom());
        return entity;
    }
}
