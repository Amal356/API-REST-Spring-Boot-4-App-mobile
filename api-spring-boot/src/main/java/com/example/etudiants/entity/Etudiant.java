package com.example.etudiants.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cin;
    private String nom;
    private LocalDate dateNaissance;
    private String email;
    private int anneePremiereInscription;

    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    public int age() {
        if (this.dateNaissance == null) return 0;
        return Period.between(this.dateNaissance, LocalDate.now()).getYears();
    }

    public int age(LocalDate referenceDate) {
        if (this.dateNaissance == null || referenceDate == null) return 0;
        return Period.between(this.dateNaissance, referenceDate).getYears();
    }
}
