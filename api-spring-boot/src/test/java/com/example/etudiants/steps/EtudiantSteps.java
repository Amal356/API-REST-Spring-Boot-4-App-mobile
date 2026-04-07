package com.example.etudiants.steps;

import io.cucumber.java.en.*;
import java.time.LocalDate;
import java.time.Period;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtudiantSteps {

    private int age;

    @Given("un étudiant avec la date de naissance {string}")
    public void unEtudiantAvecLaDateDeNaissance(String date) {
        LocalDate dateNaissance = LocalDate.parse(date);
        age = Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    @When("on calcule son âge")
    public void onCalculeSonAge() {
        // L'âge est déjà calculé dans le @Given
    }

    @Then("l'âge retourné doit être {int}")
    public void lAgeRetourneDoitEtre(int ageAttendu) {
        assertEquals(ageAttendu, age);
    }
}
