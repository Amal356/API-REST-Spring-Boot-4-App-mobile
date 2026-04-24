package com.example.etudiants.mapper;

import com.example.etudiants.dto.DepartementDTO;
import com.example.etudiants.entity.Departement;

/**
 * Mapper : assure la conversion DTO <-> Entité Departement
 * (Exigence Q8 - Architecture en couches propre)
 */
public class DepartementMapper {

    public static DepartementDTO toDto(Departement entity) {
        if (entity == null) return null;
        return new DepartementDTO(entity.getId(), entity.getNom());
    }

    public static Departement toEntity(DepartementDTO dto) {
        if (dto == null) return null;
        Departement entity = new Departement();
        entity.setNom(dto.getNom());
        return entity;
    }
}
