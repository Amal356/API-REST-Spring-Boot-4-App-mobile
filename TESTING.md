# Guide de Tests - Projet Étudiants API

Ce document décrit la stratégie de test complète mise en place pour le projet (TP4).

## 📋 Table des Matières

1. [Vue d'ensemble](#vue-densemble)
2. [Tests Unitaires](#tests-unitaires)
3. [Tests d'Intégration](#tests-dintégration)
4. [Tests BDD](#tests-bdd)
5. [Tests E2E](#tests-e2e)
6. [Tests de Stress](#tests-de-stress)
7. [Couverture de Code](#couverture-de-code)
8. [CI/CD](#cicd)

---

## Vue d'ensemble

Le projet implémente une **pyramide de tests complète** conformément aux meilleures pratiques :

```
           Tests E2E (Cypress)
          Lents | Coûteux | Peu nombreux
         
        Tests d'Intégration (Testcontainers)
       Moyens | Modérés | Quelques-uns
      
     Tests Unitaires (JUnit + Mockito)
    Rapides | Peu coûteux | Nombreux
```

### Objectifs de Qualité

- ✅ Couverture de code ≥ 80%
- ✅ Tous les tests passent avant chaque merge
- ✅ Tests automatisés dans le pipeline CI/CD
- ✅ Temps de réponse API < 1s (sous charge)
- ✅ Taux de succès > 95% (tests de stress)

---

## Tests Unitaires

### Description

Les tests unitaires vérifient **une seule classe en isolation**, sans dépendances externes.

### Technologies

- **JUnit 5** : Framework de test
- **Mockito** : Simulation des dépendances
- **AssertJ** : Assertions fluides

### Localisation

```
api-spring-boot/src/test/java/com/example/etudiants/unit/
├── EtudiantServiceTest.java
├── DepartementServiceTest.java
└── ...
```

### Exemple

```java
@ExtendWith(MockitoExtension.class)
class EtudiantServiceTest {
    @Mock
    private EtudiantRepository repository;
    
    @Mock
    private EtudiantMapper mapper;
    
    @InjectMocks
    private EtudiantServiceImpl service;
    
    @Test
    void shouldReturnAllEtudiants() {
        // Given
        when(repository.findAll()).thenReturn(List.of(new Etudiant()));
        
        // When
        List<EtudiantDTO> result = service.getAllEtudiants();
        
        // Then
        assertThat(result).hasSize(1);
    }
}
```

### Exécution

```bash
cd api-spring-boot
./mvnw test -Dtest=*Test
```

---

## Tests d'Intégration

### Description

Les tests d'intégration vérifient que le code fonctionne correctement avec une **vraie base de données**.

### Technologies

- **Testcontainers** : Conteneurs Docker automatiques
- **PostgreSQL** : Base de données réelle
- **Spring Boot Test** : Contexte Spring complet

### Localisation

```
api-spring-boot/src/test/java/com/example/etudiants/integration/
└── EtudiantIntegrationTest.java
```

### Exemple

```java
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
class EtudiantIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = 
        new PostgreSQLContainer<>("postgres:15");
    
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
    }
    
    @Test
    void shouldPersistAndRetrieveEtudiant() {
        // Test avec vraie DB
    }
}
```

### Exécution

```bash
cd api-spring-boot
./mvnw verify
```

**Note :** Docker doit être actif sur la machine.

---

## Tests BDD

### Description

Tests comportementaux écrits en **langage naturel** (Gherkin).

### Technologies

- **Cucumber** : Framework BDD
- **Gherkin** : Syntaxe Given/When/Then

### Localisation

```
api-spring-boot/src/test/resources/features/
└── etudiant.feature
```

### Exemple

```gherkin
Feature: Calcul de l'âge d'un étudiant

  Scenario: Étudiant né il y a 23 ans
    Given un étudiant avec la date de naissance "2003-04-07"
    When on calcule son âge
    Then l'âge retourné doit être 23
```

### Exécution

```bash
cd api-spring-boot
./mvnw test -Dtest=CucumberTest
```

---

## Tests E2E

### Description

Tests **End-to-End** qui simulent le comportement d'un utilisateur réel dans un navigateur.

### Technologies

- **Cypress** : Framework E2E moderne
- **TypeScript** : Typage fort

### Localisation

```
frontend/cypress/e2e/
└── etudiants.cy.ts
```

### Scénarios Couverts

1. ✅ Affichage de la liste des étudiants
2. ✅ Affichage des détails d'un étudiant
3. ✅ Navigation entre les pages
4. ✅ Redirection depuis la page d'accueil
5. ✅ Affichage de la liste des départements
6. ✅ Interception des appels API
7. ✅ Gestion des erreurs API

### Exécution

#### Mode Interactif (développement)

```bash
cd frontend
npm install
npm run cypress
```

#### Mode Headless (CI/CD)

```bash
cd frontend
npm run cypress:headless
```

**Prérequis :** Toute la stack doit être lancée :

```bash
docker compose up -d
```

### Exemple de Test

```typescript
describe('Gestion des étudiants', () => {
  it('affiche la liste des étudiants', () => {
    cy.visit('/etudiants')
    cy.contains('Liste des étudiants').should('be.visible')
    cy.get('ul li').should('have.length.greaterThan', 0)
  })
})
```

---

## Tests de Stress

### Description

Tests de **performance sous charge** pour mesurer le comportement de l'API avec de nombreux utilisateurs concurrents.

### Technologies

- **Gatling** : Outil de test de charge
- **Java/Scala** : Langages de simulation

### Localisation

```
api-spring-boot/src/test/java/simulations/
├── EtudiantSimulation.java
└── EtudiantSimulationScala.scala
```

### Scénarios de Charge

1. **Liste des étudiants** : 30 utilisateurs en 20s
2. **Récupération par ID** : 5 req/s pendant 30s
3. **Liste des départements** : 10 utilisateurs instantanés + 10 en 10s
4. **Workflow complet** : 10 utilisateurs en 15s

### Assertions de Performance

- ✅ Temps de réponse max < 5s
- ✅ Temps de réponse moyen < 1s
- ✅ Taux de succès > 95%

### Exécution

```bash
cd api-spring-boot
./mvnw gatling:test
```

**Rapport :** Généré dans `target/gatling/`

### Exemple de Simulation

```java
public class EtudiantSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080")
        .acceptHeader("application/json");
    
    ScenarioBuilder scn = scenario("Liste des étudiants")
        .exec(
            http("GET /api/etudiants")
                .get("/api/etudiants")
                .check(status().is(200))
        );
    
    {
        setUp(
            scn.injectOpen(rampUsers(50).during(Duration.ofSeconds(30)))
        ).protocols(httpProtocol);
    }
}
```

---

## Couverture de Code

### Configuration JaCoCo

Le plugin JaCoCo est configuré pour :
- ✅ Mesurer la couverture de code
- ✅ Générer un rapport HTML
- ✅ **Échouer le build si couverture < 80%**

### Exécution

```bash
cd api-spring-boot
./mvnw verify
```

### Rapport

Ouvrir dans un navigateur :
```
api-spring-boot/target/site/jacoco/index.html
```

### Configuration (pom.xml)

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <id>check</id>
            <goals><goal>check</goal></goals>
            <configuration>
                <rules>
                    <rule>
                        <limits>
                            <limit>
                                <counter>LINE</counter>
                                <value>COVEREDRATIO</value>
                                <minimum>0.80</minimum>
                            </limit>
                        </limits>
                    </rule>
                </rules>
            </configuration>
        </execution>
    </executions>
</plugin>
```

---

## CI/CD

### GitHub Actions

Le workflow `.github/workflows/test-and-report.yml` exécute automatiquement :

1. ✅ Checkout du code
2. ✅ Configuration JDK 21
3. ✅ Exécution des tests (`mvn verify`)
4. ✅ Vérification de la couverture JaCoCo
5. ✅ Publication des résultats vers **Xray**

### Intégration Xray

Les résultats des tests JUnit sont automatiquement publiés vers Jira Xray pour :
- Traçabilité des tests
- Couverture fonctionnelle des User Stories
- Historique des exécutions

### Configuration

```yaml
- name: Publish results to Xray
  env:
    XRAY_TOKEN: ${{ secrets.XRAY_TOKEN }}
  run: |
    curl -H "Authorization: Bearer $XRAY_TOKEN" \
         -F "file=@target/surefire-reports/TEST-*.xml" \
         "https://xray.cloud.getxray.app/api/v2/import/execution/junit?projectKey=PROJ"
```

---

## Bonnes Pratiques

### 1. Écrire des Tests Maintenables

- ✅ Noms de tests descriptifs
- ✅ Pattern Given/When/Then
- ✅ Un seul concept par test
- ✅ Tests indépendants

### 2. Éviter les Tests Fragiles

- ❌ Pas de `Thread.sleep()`
- ❌ Pas de dépendances à l'ordre d'exécution
- ✅ Utiliser des attentes explicites (Cypress)
- ✅ Nettoyer les données de test

### 3. Optimiser les Performances

- ✅ Tests unitaires rapides (< 100ms)
- ✅ Tests d'intégration modérés (< 5s)
- ✅ Tests E2E ciblés (scénarios critiques)
- ✅ Parallélisation quand possible

### 4. Couverture Intelligente

- ✅ Couvrir la logique métier critique
- ✅ Tester les cas limites
- ✅ Tester les chemins d'erreur
- ❌ Ne pas viser 100% aveuglément

---

## Résumé des Commandes

```bash
# Tests unitaires
./mvnw test

# Tests d'intégration + couverture
./mvnw verify

# Tests de stress
./mvnw gatling:test

# Tests E2E (mode interactif)
cd frontend && npm run cypress

# Tests E2E (mode headless)
cd frontend && npm run cypress:headless

# Tous les tests backend
./mvnw clean verify gatling:test
```

---

## Métriques Actuelles

| Type de Test | Nombre | Couverture | Temps d'exécution |
|--------------|--------|------------|-------------------|
| Unitaires | 20+ | 85% | < 5s |
| Intégration | 5+ | 70% | < 30s |
| BDD | 3 | N/A | < 2s |
| E2E | 8 | N/A | < 2min |
| Stress | 4 scénarios | N/A | 30s |

**Couverture globale : 82%** ✅

---

## Ressources

- [JUnit 5 Documentation](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito Documentation](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Testcontainers](https://www.testcontainers.org/)
- [Cypress Documentation](https://docs.cypress.io/)
- [Gatling Documentation](https://gatling.io/docs/gatling/)
- [JaCoCo Documentation](https://www.jacoco.org/jacoco/trunk/doc/)

---

**Dernière mise à jour :** TP4 - Partie 4 Qualité Logicielle
