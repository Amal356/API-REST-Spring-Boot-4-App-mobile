package com.example.etudiants.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

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

    // Constructors
    public EtudiantSimpleDTO() {}

    public EtudiantSimpleDTO(Long id, String cin, String nom, LocalDate dateNaissance, String email, int anneePremiereInscription, Long departementId, String departementNom, int age) {
        this.id = id;
        this.cin = cin;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.anneePremiereInscription = anneePremiereInscription;
        this.departementId = departementId;
        this.departementNom = departementNom;
        this.age = age;
    }

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
