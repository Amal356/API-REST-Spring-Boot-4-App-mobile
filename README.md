# Etudiants API - TP1 + TP2 + TP3

Projet complet autour de la gestion d'etudiants:
- `etudiant-service` (Spring Boot existant)
- `grading-service` (microservice notes)
- `eureka-server` (service discovery)
- `api-gateway` (point d'entree unique)
- `frontend` Next.js
- applications mobiles Flutter et React Native

## Prerequis
- Java 21
- Maven 3.9+
- Docker + Docker Compose
- Node.js 20+ (si lancement frontend hors Docker)

## Lancement complet (recommande)

Depuis la racine:

```bash
docker compose up -d --build
```

## URLs utiles
- Eureka: `http://localhost:8761`
- API Gateway: `http://localhost:8080`
- Etudiant service direct: `http://localhost:8081`
- Grading service direct: `http://localhost:8082`
- Frontend Next.js: `http://localhost:3000`

## Endpoints via API Gateway
- Etudiants:
  - `GET /api/etudiants`
  - `GET /api/etudiants/{id}`
  - `POST /api/etudiants`
  - `PUT /api/etudiants/{id}`
  - `DELETE /api/etudiants/{id}`
- Departements:
  - `GET /api/departements`
  - `GET /api/departements/{id}`
  - `POST /api/departements`
  - `PUT /api/departements/{id}`
  - `DELETE /api/departements/{id}`
- Notes:
  - `GET /api/notes`
  - `GET /api/notes/{id}`
  - `POST /api/notes`
  - `PUT /api/notes/{id}`
  - `DELETE /api/notes/{id}`

## Tests

### Tests Backend (Service Etudiants)

#### Tests Unitaires et d'Intégration
```bash
cd api-spring-boot
./mvnw test
```

#### Couverture de Code (JaCoCo)
```bash
cd api-spring-boot
./mvnw verify
# Rapport disponible dans: target/site/jacoco/index.html
```

#### Tests de Stress (Gatling)
```bash
cd api-spring-boot
./mvnw gatling:test
# Rapport disponible dans: target/gatling/
```

### Tests E2E Frontend (Cypress)

#### Mode Interactif
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

**Note:** Assurez-vous que toute la stack est lancée (`docker compose up`) avant de lancer les tests E2E.

### Stratégie de Test Complète (TP4)

Le projet implémente une pyramide de tests complète :

1. **Tests Unitaires** (JUnit 5 + Mockito)
   - Localisation: `api-spring-boot/src/test/java/.../unit/`
   - Cible: Couche Service (logique métier isolée)
   - Exécution rapide sans dépendances externes

2. **Tests d'Intégration** (Testcontainers)
   - Localisation: `api-spring-boot/src/test/java/.../integration/`
   - Cible: Interaction avec PostgreSQL réel
   - Conteneur Docker automatique pendant les tests

3. **Tests BDD** (Cucumber)
   - Localisation: `api-spring-boot/src/test/resources/features/`
   - Format: Gherkin (Given/When/Then)
   - Tests comportementaux lisibles par tous

4. **Tests E2E** (Cypress)
   - Localisation: `frontend/cypress/e2e/`
   - Cible: Parcours utilisateur complet dans le navigateur
   - Vérifie l'intégration frontend ↔ backend

5. **Tests de Stress** (Gatling)
   - Localisation: `api-spring-boot/src/test/java/simulations/`
   - Cible: Performance sous charge (50+ utilisateurs concurrents)
   - Mesure temps de réponse et taux de succès

### Couverture de Code

Objectif: **≥ 80%** (configuré dans JaCoCo)
- Le build Maven échoue si la couverture descend sous 80%
- Rapport détaillé généré après `mvn verify`

## Test fonctionnel Partie 3
```bash
powershell -NoProfile -ExecutionPolicy Bypass -File scripts/test-part3.ps1
```

## Docker Hub
Image utilisee pour le service etudiants:
- `amal878/etudiant-service:1.0`

```bash
docker pull amal878/etudiant-service:1.0
```

## Workflow GitHub (TP3-Q2)

Le depot contient:
- template issue bug: `.github/ISSUE_TEMPLATE/bug_report.md`
- template issue feature: `.github/ISSUE_TEMPLATE/feature_request.md`
- template PR: `.github/pull_request_template.md`

Convention de review:
- toute fonctionnalite passe par PR vers `version-3` puis `main`
- au moins une relecture (auto-review si travail solo)
- commentaires bloquants resolus avant merge
- ticket Jira lie dans chaque PR

## Jira

### Sprints

- **Sprint 1 + Sprint 2**: API REST de base + Enrichissement
  ![Board Jira Sprint 1 et Sprint 2](jira-board.png)

- **Sprint 3**: Architecture Microservices
  ![Board Jira Sprint 3](capture Sprint 3 Jira.png)

- **Sprint 4**: Qualité Logicielle (Tests + Intégrations)
  - Tests unitaires et d'intégration avec Testcontainers
  - Tests E2E avec Cypress
  - Tests de stress avec Gatling
  - Intégration GitHub ↔ Jira
  - Intégration Xray pour gestion des cas de test
  - Service d'authentification (Express + MongoDB + JWT)

### Intégration GitHub ↔ Jira

Le projet utilise l'application **GitHub for Jira** pour lier automatiquement :
- Commits aux tickets Jira (via clé dans le message)
- Branches aux tickets (via clé dans le nom)
- Pull Requests aux tickets (via clé dans le titre)

**Convention de nommage :**
```bash
# Branche
git checkout -b feature/PROJ-23-ajout-tests-e2e

# Commit
git commit -m "PROJ-23 : ajout tests E2E Cypress pour étudiants"

# PR Title
"PROJ-23 : Tests E2E et couverture complète"
```

### Intégration Xray

Les résultats des tests JUnit sont automatiquement publiés vers Xray via GitHub Actions.
Voir `.github/workflows/test-and-report.yml` pour la configuration.


## Architecture de Tests (TP4)

### Pyramide de Tests

```
           /\
          /  \    Tests E2E (Cypress)
         /____\   - Lents, coûteux
        /      \  - Parcours utilisateur complet
       /________\ 
      /          \ Tests d'Intégration (Testcontainers)
     /____________\  - Moyennement rapides
    /              \ - Vérifient la collaboration entre composants
   /________________\
  /                  \ Tests Unitaires (JUnit + Mockito)
 /____________________\ - Rapides, nombreux
                        - Vérifient la logique métier isolée
```

### Métriques de Qualité

| Métrique | Objectif | Outil | Statut |
|----------|----------|-------|--------|
| Couverture de code | ≥ 80% | JaCoCo | ✅ Configuré |
| Tests unitaires | > 20 tests | JUnit 5 | ✅ Implémenté |
| Tests d'intégration | > 5 tests | Testcontainers | ✅ Implémenté |
| Tests E2E | > 5 scénarios | Cypress | ✅ Implémenté |
| Tests de stress | 50+ users | Gatling | ✅ Implémenté |
| Temps de réponse moyen | < 1s | Gatling | ✅ Vérifié |
| Taux de succès | > 95% | Gatling | ✅ Vérifié |

### Exécution des Tests en CI/CD

Le workflow GitHub Actions (`.github/workflows/test-and-report.yml`) exécute automatiquement :
1. Tests unitaires et d'intégration (`mvn verify`)
2. Vérification de la couverture JaCoCo
3. Publication des résultats vers Xray

Pour les tests E2E et Gatling, ils doivent être exécutés manuellement car ils nécessitent que toute la stack soit lancée.

## Services d'Authentification (TP4-Q6)

### Auth Service (Node.js + Express + MongoDB + JWT)

**Endpoints :**
- `POST /auth/register` - Inscription d'un nouvel utilisateur
- `POST /auth/login` - Connexion et obtention d'un token JWT

**Technologies :**
- Express.js pour l'API REST
- MongoDB pour le stockage des utilisateurs
- Mongoose pour la modélisation
- bcrypt pour le hachage des mots de passe
- jsonwebtoken pour l'émission de tokens JWT

**Exemple d'utilisation :**

```bash
# Inscription
curl -X POST http://localhost:3001/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"secret123"}'

# Connexion
curl -X POST http://localhost:3001/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"secret123"}'
```

Le token JWT retourné peut être utilisé pour authentifier les requêtes vers les autres microservices.

## Structure du Projet (Complète)

```
/projet-etudiants/
├── api-spring-boot/              # Microservice Étudiants (Spring Boot)
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/.../
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── entity/
│   │   │   │   ├── dto/
│   │   │   │   ├── mapper/
│   │   │   │   └── config/
│   │   │   └── resources/
│   │   │       └── static/index.html
│   │   └── test/
│   │       ├── java/
│   │       │   ├── unit/          # Tests unitaires
│   │       │   ├── integration/   # Tests d'intégration
│   │       │   └── simulations/   # Tests Gatling
│   │       └── resources/
│   │           └── features/      # Tests BDD Cucumber
│   ├── Dockerfile
│   └── pom.xml
├── grading-service/              # Microservice Notes (Spring Boot)
│   ├── src/main/java/.../
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── entity/
│   │   ├── dto/
│   │   ├── client/               # Feign Client
│   │   └── config/
│   ├── Dockerfile
│   └── pom.xml
├── eureka-server/                # Service Discovery
│   ├── src/main/java/
│   ├── Dockerfile
│   └── pom.xml
├── api-gateway/                  # API Gateway (Spring Cloud Gateway)
│   ├── src/main/
│   ├── Dockerfile
│   └── pom.xml
├── auth-service/                 # Service d'Authentification (Node.js)
│   ├── src/
│   │   ├── models/User.js
│   │   ├── routes/auth.js
│   │   └── app.js
│   ├── Dockerfile
│   └── package.json
├── frontend/                     # Application Next.js
│   ├── app/
│   │   ├── etudiants/
│   │   ├── departements/
│   │   └── layout.tsx
│   ├── cypress/                  # Tests E2E
│   │   ├── e2e/
│   │   │   └── etudiants.cy.ts
│   │   └── support/
│   ├── Dockerfile
│   ├── package.json
│   └── cypress.config.ts
├── mobile-app/                   # Application Flutter
│   └── lib/main.dart
├── react_native_app/             # Application React Native
│   └── app/(tabs)/index.tsx
├── k8s/                          # Manifests Kubernetes
│   ├── etudiant-deployment.yaml
│   └── postgres-deployment.yaml
├── .github/
│   ├── workflows/
│   │   └── test-and-report.yml   # CI/CD avec Xray
│   ├── ISSUE_TEMPLATE/
│   │   ├── bug_report.md
│   │   └── feature_request.md
│   └── pull_request_template.md
├── docker-compose.yml            # Orchestration complète
└── README.md
```

## Branches Git

- `main` : Branche principale stable
- `version-2` : TP Partie 2 (Enrichissement)
- `version-3` : TP Partie 3 (Architecture Microservices)
- `version-4` : TP Partie 4 (Qualité Logicielle)

## Contributeurs

Projet réalisé dans le cadre des TPs d'Intégration de Compétences.

## Licence

Ce projet est à usage éducatif.
