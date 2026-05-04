package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

/**
 * Simulation de test de stress pour l'API Etudiants
 * 
 * Ce test simule 50 utilisateurs concurrents qui accèdent à l'API
 * pendant 30 secondes pour mesurer les performances sous charge.
 */
public class EtudiantSimulation extends Simulation {

    // Configuration du protocole HTTP
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080")  // API Gateway
        .acceptHeader("application/json")
        .contentTypeHeader("application/json")
        .userAgentHeader("Gatling/Performance/Test");

    // Scénario 1 : Liste des étudiants
    ScenarioBuilder listeEtudiants = scenario("Liste des étudiants")
        .exec(
            http("GET /api/etudiants")
                .get("/api/etudiants")
                .check(status().is(200))
                .check(jsonPath("$").exists())
        )
        .pause(Duration.ofSeconds(1));

    // Scénario 2 : Récupération d'un étudiant par ID
    ScenarioBuilder getEtudiantById = scenario("Récupération étudiant par ID")
        .exec(
            http("GET /api/etudiants/1")
                .get("/api/etudiants/1")
                .check(status().in(200, 404))
        )
        .pause(Duration.ofMillis(500));

    // Scénario 3 : Liste des départements
    ScenarioBuilder listeDepartements = scenario("Liste des départements")
        .exec(
            http("GET /api/departements")
                .get("/api/departements")
                .check(status().is(200))
        )
        .pause(Duration.ofSeconds(1));

    // Scénario 4 : Workflow complet (navigation utilisateur)
    ScenarioBuilder workflowComplet = scenario("Workflow complet")
        .exec(
            http("GET /api/departements")
                .get("/api/departements")
                .check(status().is(200))
        )
        .pause(Duration.ofMillis(500))
        .exec(
            http("GET /api/etudiants")
                .get("/api/etudiants")
                .check(status().is(200))
        )
        .pause(Duration.ofMillis(500))
        .exec(
            http("GET /api/etudiants/1")
                .get("/api/etudiants/1")
                .check(status().in(200, 404))
        );

    // Configuration de la simulation
    {
        setUp(
            // Scénario principal : montée en charge progressive
            listeEtudiants.injectOpen(
                rampUsers(30).during(Duration.ofSeconds(20))
            ),
            
            // Scénario secondaire : charge constante
            getEtudiantById.injectOpen(
                constantUsersPerSec(5).during(Duration.ofSeconds(30))
            ),
            
            // Scénario tertiaire : pics de charge
            listeDepartements.injectOpen(
                atOnceUsers(10),
                rampUsers(10).during(Duration.ofSeconds(10))
            ),
            
            // Workflow complet : utilisateurs réalistes
            workflowComplet.injectOpen(
                rampUsers(10).during(Duration.ofSeconds(15))
            )
        )
        .protocols(httpProtocol)
        .assertions(
            // Assertions sur les performances
            global().responseTime().max().lt(5000),  // Temps de réponse max < 5s
            global().responseTime().mean().lt(1000), // Temps de réponse moyen < 1s
            global().successfulRequests().percent().gt(95.0) // Taux de succès > 95%
        );
    }
}
