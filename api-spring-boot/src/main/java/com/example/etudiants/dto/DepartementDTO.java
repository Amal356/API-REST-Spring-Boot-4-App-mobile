package com.example.etudiants.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartementDTO implements Serializable {
    private Long id;

    @NotBlank(message = "Le nom du departement est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom du departement doit contenir entre 2 et 100 caracteres")
    private String nom;
}
