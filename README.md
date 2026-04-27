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

- Tests backend (service etudiants):
```bash
cd api-spring-boot
./mvnw test
```

- Test fonctionnel Partie 3:
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

- Capture board Sprint 1 + Sprint 2:

![Board Jira Sprint 1 et Sprint 2](jira-board.png)

- Sprint 3: ajouter votre capture board Sprint 3 ici:

`![Board Jira Sprint 3](<votre-capture-sprint3.png>)`
