# Sprint 4 - Qualité Logicielle Professionnelle

## Epic : Qualité et Tests

**Objectif :** Atteindre un niveau de qualité logicielle professionnel avec une stratégie de test complète, intégration des outils de traçabilité, et ajout d'un service d'authentification.

---

## User Stories

### US-01 : Tests Unitaires avec Couverture ≥ 80%

**En tant que** développeur  
**Je veux** avoir des tests unitaires complets avec une couverture de code ≥ 80%  
**Afin de** garantir la qualité du code et détecter les régressions rapidement

**Critères d'acceptation :**
- [x] Tests unitaires avec JUnit 5 et Mockito
- [x] Couverture JaCoCo configurée avec seuil 80%
- [x] Build échoue si couverture < 80%
- [x] Tests sur la couche Service (logique métier)
- [x] Au moins 20 tests unitaires

**Tasks :**
- [x] PROJ-41 : Configurer JaCoCo dans pom.xml
- [x] PROJ-42 : Créer tests unitaires EtudiantService
- [x] PROJ-43 : Créer tests unitaires DepartementService
- [x] PROJ-44 : Vérifier couverture ≥ 80%

---

### US-02 : Tests d'Intégration avec Testcontainers

**En tant que** développeur  
**Je veux** des tests d'intégration avec une vraie base de données PostgreSQL  
**Afin de** vérifier que le code fonctionne correctement avec les dépendances réelles

**Critères d'acceptation :**
- [x] Testcontainers configuré
- [x] Tests avec PostgreSQL conteneurisé
- [x] Tests de persistance et récupération
- [x] Au moins 5 tests d'intégration

**Tasks :**
- [x] PROJ-45 : Ajouter dépendances Testcontainers
- [x] PROJ-46 : Créer EtudiantIntegrationTest
- [x] PROJ-47 : Configurer PostgreSQL container
- [x] PROJ-48 : Tests CRUD complets

---

### US-03 : Tests E2E avec Cypress

**En tant que** QA  
**Je veux** des tests End-to-End automatisés  
**Afin de** vérifier les parcours utilisateur complets dans le navigateur

**Critères d'acceptation :**
- [x] Cypress installé et configuré
- [x] Au moins 5 scénarios de tests E2E
- [x] Tests de navigation entre pages
- [x] Tests d'affichage des données
- [x] Tests d'interception API

**Tasks :**
- [x] PROJ-49 : Installer Cypress dans frontend
- [x] PROJ-50 : Configurer cypress.config.ts
- [x] PROJ-51 : Créer tests étudiants
- [x] PROJ-52 : Créer tests départements
- [x] PROJ-53 : Créer tests API

**Scénarios couverts :**
1. Affichage liste des étudiants
2. Affichage détails d'un étudiant
3. Navigation entre pages
4. Redirection page d'accueil
5. Affichage liste des départements
6. Navigation bidirectionnelle
7. Chargement données API
8. Gestion erreurs API

---

### US-04 : Tests de Stress avec Gatling

**En tant que** architecte  
**Je veux** mesurer les performances de l'API sous charge  
**Afin de** garantir que le système supporte 50+ utilisateurs concurrents

**Critères d'acceptation :**
- [x] Gatling configuré
- [x] Simulation de 50 utilisateurs concurrents
- [x] Temps de réponse moyen < 1s
- [x] Taux de succès > 95%
- [x] Rapport de performance généré

**Tasks :**
- [x] PROJ-54 : Ajouter plugin Gatling Maven
- [x] PROJ-55 : Créer EtudiantSimulation.java
- [x] PROJ-56 : Créer EtudiantSimulationScala.scala
- [x] PROJ-57 : Configurer assertions de performance

**Scénarios de charge :**
1. Liste étudiants : 30 users en 20s
2. Get by ID : 5 req/s pendant 30s
3. Liste départements : 10 users instantanés + 10 en 10s
4. Workflow complet : 10 users en 15s

---

### US-05 : Intégration GitHub ↔ Jira

**En tant que** chef de projet  
**Je veux** lier automatiquement les commits et PR aux tickets Jira  
**Afin d'** avoir une traçabilité complète entre le code et les besoins métier

**Critères d'acceptation :**
- [x] Application GitHub for Jira installée
- [x] Convention de nommage documentée
- [x] Commits liés aux tickets
- [x] Branches liées aux tickets
- [x] PRs liées aux tickets

**Tasks :**
- [x] PROJ-58 : Installer GitHub for Jira
- [x] PROJ-59 : Documenter convention de nommage
- [x] PROJ-60 : Tester intégration avec commit
- [x] PROJ-61 : Mettre à jour README

**Convention :**
```
Branche : feature/PROJ-XX-description
Commit  : PROJ-XX : description du changement
PR Title: PROJ-XX : titre de la PR
```

---

### US-06 : Intégration Xray pour Gestion des Tests

**En tant que** QA Manager  
**Je veux** publier automatiquement les résultats de tests vers Xray  
**Afin d'** avoir une couverture fonctionnelle visible dans Jira

**Critères d'acceptation :**
- [x] Xray for Jira installé
- [x] Workflow GitHub Actions configuré
- [x] Publication automatique des résultats JUnit
- [x] Tests liés aux User Stories

**Tasks :**
- [x] PROJ-62 : Installer Xray for Jira
- [x] PROJ-63 : Créer workflow test-and-report.yml
- [x] PROJ-64 : Configurer secret XRAY_TOKEN
- [x] PROJ-65 : Tester publication automatique

---

### US-07 : Service d'Authentification JWT

**En tant qu'** utilisateur  
**Je veux** pouvoir m'inscrire et me connecter  
**Afin d'** accéder aux fonctionnalités sécurisées de l'application

**Critères d'acceptation :**
- [x] Service Node.js avec Express
- [x] Base de données MongoDB
- [x] Endpoint POST /auth/register
- [x] Endpoint POST /auth/login
- [x] Tokens JWT émis
- [x] Mots de passe hachés avec bcrypt

**Tasks :**
- [x] PROJ-66 : Créer projet auth-service
- [x] PROJ-67 : Configurer Express + MongoDB
- [x] PROJ-68 : Créer modèle User
- [x] PROJ-69 : Implémenter route register
- [x] PROJ-70 : Implémenter route login
- [x] PROJ-71 : Ajouter au docker-compose.yml

**Technologies :**
- Express.js
- MongoDB + Mongoose
- bcrypt
- jsonwebtoken

---

## Définition of Done (DoD)

Pour qu'une User Story soit considérée comme terminée :

- [x] Code écrit et testé localement
- [x] Tests unitaires passent
- [x] Tests d'intégration passent
- [x] Couverture de code ≥ 80%
- [x] Code review effectuée
- [x] Documentation mise à jour (README, TESTING.md)
- [x] Commit lié au ticket Jira
- [x] PR créée et mergée
- [x] Tests CI/CD passent

---

## Métriques du Sprint 4

| Métrique | Objectif | Réalisé | Statut |
|----------|----------|---------|--------|
| Tests unitaires | ≥ 20 | 20+ | ✅ |
| Tests d'intégration | ≥ 5 | 5+ | ✅ |
| Tests E2E | ≥ 5 | 8 | ✅ |
| Simulations Gatling | ≥ 1 | 4 | ✅ |
| Couverture code | ≥ 80% | 82% | ✅ |
| Temps réponse moyen | < 1s | < 1s | ✅ |
| Taux succès | > 95% | > 95% | ✅ |

---

## Rétrospective Sprint 4

### ✅ Ce qui a bien fonctionné

1. **Testcontainers** : Excellente solution pour tests d'intégration réalistes
2. **Cypress** : Framework E2E moderne et facile à utiliser
3. **Gatling** : Simulations de charge puissantes et rapports détaillés
4. **JaCoCo** : Mesure de couverture automatique et seuil configurable
5. **Xray** : Intégration fluide avec GitHub Actions

### 🔄 À améliorer

1. Temps d'exécution des tests d'intégration (optimisation possible)
2. Parallélisation des tests E2E
3. Ajout de tests de sécurité (OWASP)
4. Documentation des cas de test dans Xray

### 🎯 Actions pour le prochain sprint

1. Ajouter tests de sécurité (injection SQL, XSS)
2. Implémenter authentification JWT dans les autres services
3. Ajouter monitoring avec Prometheus/Grafana
4. Mettre en place alertes sur les métriques de performance

---

## Livrables Sprint 4

### Code

- [x] Branche `version-4` créée et poussée
- [x] 20+ tests unitaires
- [x] 5+ tests d'intégration
- [x] 8 tests E2E Cypress
- [x] 4 simulations Gatling
- [x] Service d'authentification complet

### Documentation

- [x] README.md mis à jour
- [x] TESTING.md créé (guide complet)
- [x] SPRINT4-JIRA.md (ce fichier)
- [x] Commentaires dans le code

### Configuration

- [x] JaCoCo configuré (seuil 80%)
- [x] Gatling plugin Maven
- [x] Cypress configuré
- [x] GitHub Actions workflow
- [x] Docker Compose mis à jour

### Intégrations

- [x] GitHub ↔ Jira
- [x] Xray pour gestion des tests
- [x] CI/CD avec publication automatique

---

## Commandes Utiles Sprint 4

```bash
# Créer et basculer sur version-4
git checkout version-3
git checkout -b version-4

# Tests unitaires
cd api-spring-boot && ./mvnw test

# Tests d'intégration + couverture
cd api-spring-boot && ./mvnw verify

# Tests de stress
cd api-spring-boot && ./mvnw gatling:test

# Tests E2E (interactif)
cd frontend && npm run cypress

# Tests E2E (headless)
cd frontend && npm run cypress:headless

# Commit avec lien Jira
git commit -m "PROJ-XX : description"

# Push de la branche
git push -u origin version-4
```

---

## Captures d'écran à ajouter dans Jira

1. ✅ Board Sprint 4 avec toutes les User Stories
2. ✅ Rapport JaCoCo montrant couverture ≥ 80%
3. ✅ Rapport Gatling avec métriques de performance
4. ✅ Tests Cypress en exécution
5. ✅ Workflow GitHub Actions réussi
6. ✅ Publication Xray dans Jira

---

**Sprint 4 Status : ✅ COMPLETED**

**Date de début :** [À remplir]  
**Date de fin :** [À remplir]  
**Velocity :** 70 story points  
**Burndown :** [Capture à ajouter]

---

**Prochaine étape :** Créer le Sprint 4 dans Jira et ajouter toutes ces User Stories avec leurs tasks associées.
