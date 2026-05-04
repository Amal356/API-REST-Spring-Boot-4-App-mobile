package com.example.etudiants.unit;

import com.example.etudiants.dto.EtudiantDTO;
import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.mapper.EtudiantMapper;
import com.example.etudiants.repository.EtudiantRepository;
import com.example.etudiants.service.EtudiantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {

    @Mock
    private EtudiantRepository repository;

    @Mock
    private EtudiantMapper mapper;

    @InjectMocks
    private EtudiantServiceImpl service;

    @Test
    void shouldReturnAllEtudiants() {
        Departement dept = new Departement();
        dept.setId(1L);
        dept.setNom("Informatique");

        Etudiant etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setCin("12345678");
        etudiant.setNom("Ali Ben Salah");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 1));
        etudiant.setDepartement(dept);

        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(1L);
        dto.setCin("12345678");
        dto.setNom("Ali Ben Salah");

        when(repository.findAll()).thenReturn(List.of(etudiant));
        when(mapper.toDto(etudiant)).thenReturn(dto);

        List<EtudiantDTO> result = service.getAllEtudiants();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Ali Ben Salah");
    }

    @Test
    void shouldReturnEtudiantById() {
        Departement dept = new Departement();
        dept.setId(1L);
        dept.setNom("Informatique");

        Etudiant etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setCin("12345678");
        etudiant.setNom("Ali Ben Salah");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 1));
        etudiant.setDepartement(dept);

        EtudiantDTO dto = new EtudiantDTO();
        dto.setId(1L);
        dto.setCin("12345678");
        dto.setNom("Ali Ben Salah");

        when(repository.findById(1L)).thenReturn(Optional.of(etudiant));
        when(mapper.toDto(etudiant)).thenReturn(dto);

        EtudiantDTO result = service.getEtudiantById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Ali Ben Salah");
    }

    @Test
    void shouldCreateEtudiant() {
        Departement dept = new Departement();
        dept.setId(1L);
        dept.setNom("Informatique");

        EtudiantDTO inputDto = new EtudiantDTO();
        inputDto.setCin("12345678");
        inputDto.setNom("Ali Ben Salah");
        inputDto.setDateNaissance(LocalDate.of(2000, 1, 1));

        Etudiant etudiant = new Etudiant();
        etudiant.setId(1L);
        etudiant.setCin("12345678");
        etudiant.setNom("Ali Ben Salah");
        etudiant.setDateNaissance(LocalDate.of(2000, 1, 1));
        etudiant.setDepartement(dept);

        EtudiantDTO outputDto = new EtudiantDTO();
        outputDto.setId(1L);
        outputDto.setCin("12345678");
        outputDto.setNom("Ali Ben Salah");

        when(mapper.toEntity(any(EtudiantDTO.class))).thenReturn(etudiant);
        when(repository.save(any(Etudiant.class))).thenReturn(etudiant);
        when(mapper.toDto(etudiant)).thenReturn(outputDto);

        EtudiantDTO result = service.createEtudiant(inputDto);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }
}
