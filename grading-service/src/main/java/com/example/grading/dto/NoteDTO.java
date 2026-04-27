package com.example.grading.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteDTO {
    private Long id;

    @NotNull(message = "studentId est obligatoire")
    private Long studentId;

    @NotBlank(message = "matiere est obligatoire")
    private String matiere;

    @NotNull(message = "valeur est obligatoire")
    @Min(value = 0, message = "valeur doit etre >= 0")
    @Max(value = 20, message = "valeur doit etre <= 20")
    private Double valeur;
}

