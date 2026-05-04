package com.example.etudiants.integration;

import com.example.etudiants.entity.Departement;
import com.example.etudiants.entity.Etudiant;
import com.example.etudiants.repository.DepartementRepository;
import com.example.etudiants.repository.EtudiantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class EtudiantIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Test
    void shouldPersistAndRetrieveEtudiant() {
        Departement dept = new Departement();
        dept.setNom("Informatique");
        departementRepository.save(dept);

        Etudiant e = new Etudiant();
        e.setCin("12345678");
        e.setNom("Dupont");
        e.setDateNaissance(LocalDate.of(2000, 1, 1));
        e.setDepartement(dept);
        etudiantRepository.save(e);

        List<Etudiant> etudiants = etudiantRepository.findAll();
        assertThat(etudiants).isNotEmpty();
        assertThat(etudiants.get(0).getNom()).isEqualTo("Dupont");
    }
}
