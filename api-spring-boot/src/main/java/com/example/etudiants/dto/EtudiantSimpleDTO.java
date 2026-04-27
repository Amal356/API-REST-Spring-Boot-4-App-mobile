package com.example.etudiants.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EtudiantSimpleDTO {
    private Long id;
    private String cin;
    private String nom;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;
    
    private String email;
    private int anneePremiereInscription;
    private Long departementId;
    private String departementNom;
    private int age;
}
