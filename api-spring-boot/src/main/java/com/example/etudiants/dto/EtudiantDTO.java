package com.example.etudiants.dto;

import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtudiantDTO implements Serializable {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("cin")
    @NotBlank(message = "Le CIN est obligatoire")
    @Size(min = 8, max = 8, message = "Le CIN doit contenir 8 caracteres")
    @Pattern(regexp = "\\d{8}", message = "Le CIN doit contenir uniquement 8 chiffres")
    private String cin;

    @JsonProperty("nom")
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caracteres")
    private String nom;

    @JsonProperty("dateNaissance")
    @JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit etre dans le passe")
    private LocalDate dateNaissance;

    @JsonProperty("email")
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @JsonProperty("anneePremiereInscription")
    @Min(value = 2000, message = "L'annee d'inscription doit etre >= 2000")
    @Max(value = 2100, message = "L'annee d'inscription doit etre <= 2100")
    private int anneePremiereInscription;

    @JsonProperty("departementId")
    private Long departementId;

    @JsonProperty("departementNom")
    private String departementNom;

    @JsonProperty("age")
    private int age;
}
