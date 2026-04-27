package com.example.etudiants.steps;

import com.example.etudiants.entity.Etudiant;
import io.cucumber.java.en.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtudiantSteps {

    private Etudiant etudiant;
    private int age;
    private final LocalDate referenceDate = LocalDate.of(2026, 4, 7);

    @Given("un étudiant avec la date de naissance {string}")
    public void unEtudiantAvecLaDateDeNaissance(String date) {
        LocalDate dateNaissance = LocalDate.parse(date);
        etudiant = new Etudiant();
        etudiant.setDateNaissance(dateNaissance);
    }

    @When("on calcule son âge")
    public void onCalculeSonAge() {
        age = etudiant.age(referenceDate);
    }

    @Then("l'âge retourné doit être {int}")
    public void lAgeRetourneDoitEtre(int ageAttendu) {
        assertEquals(ageAttendu, age);
    }
}
