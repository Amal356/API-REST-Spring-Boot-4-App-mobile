package com.example.etudiants.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;

public class EtudiantDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("cin")
    private String cin;

    @JsonProperty("nom")
    private String nom;

    @JsonProperty("dateNaissance")
    @JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    @JsonProperty("email")
    private String email;

    @JsonProperty("anneePremiereInscription")
    private int anneePremiereInscription;

    @JsonProperty("departementId")
    private Long departementId;

    @JsonProperty("departementNom")
    private String departementNom;

    @JsonProperty("age")
    private int age;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getAnneePremiereInscription() { return anneePremiereInscription; }
    public void setAnneePremiereInscription(int anneePremiereInscription) { this.anneePremiereInscription = anneePremiereInscription; }

    public Long getDepartementId() { return departementId; }
    public void setDepartementId(Long departementId) { this.departementId = departementId; }

    public String getDepartementNom() { return departementNom; }
    public void setDepartementNom(String departementNom) { this.departementNom = departementNom; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
