# Etudiants API

Mini-projet Spring Boot pour gérer des étudiants avec PostgreSQL (Docker) et une UI statique (`index.html`).  
Swagger est activé pour documenter/tester l’API.

## Prérequis
- Java 21 (pour build local)
- Docker + Docker Compose

## Démarrage (recommandé)

Depuis la racine du projet:

```bash
docker compose up -d --build
```

## URLs utiles
- API: `http://localhost:8080/api/etudiants`
- Swagger UI: `http://localhost:8080/swagger-ui.html` (redirige vers `/swagger-ui/index.html`)
- Page web: `http://localhost:8080/`
- Test API: `http://localhost:8080/api/test`

## Endpoints principaux
- Étudiants
  - `GET /api/etudiants`
  - `GET /api/etudiants?annee=2022`
  - `GET /api/etudiants/{id}`
  - `POST /api/etudiants`
  - `PUT /api/etudiants/{id}`
  - `DELETE /api/etudiants/{id}`
- Départements
  - `GET /api/departements`
  - `GET /api/departements/{id}`
  - `POST /api/departements`
  - `PUT /api/departements/{id}`
  - `DELETE /api/departements/{id}`

## Tests

```bash
cd api-spring-boot
./mvnw test
```

## Docker Hub (image)
- `amal878/etudiant-service:1.0`

```bash
docker pull amal878/etudiant-service:1.0
```

## Jira (capture)

![Board Jira Sprint 1 et Sprint 2](jira-board.png)
