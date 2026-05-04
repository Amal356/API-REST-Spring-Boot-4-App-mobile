# 🧪 GUIDE D'EXÉCUTION DES TESTS
## Projet: API REST Spring Boot - Gestion des Étudiants
## Branche: version-4

---

## 📋 TABLE DES MATIÈRES

1. [Prérequis](#prérequis)
2. [Lancement de l'Infrastructure](#lancement-de-linfrastructure)
3. [Tests Backend](#tests-backend)
4. [Tests Frontend](#tests-frontend)
5. [Vérification des Résultats](#vérification-des-résultats)
6. [Troubleshooting](#troubleshooting)

---

## 🔧 PRÉREQUIS

### Logiciels Requis

| Logiciel | Version Minimale | Commande de Vérification |
|----------|------------------|--------------------------|
| Java | 21+ | `java --version` |
| Maven | 3.9+ | `mvn --version` |
| Docker | 20+ | `docker --version` |
| Docker Compose | 2.0+ | `docker compose version` |
| Node.js | 20+ | `node --version` |
| npm | 10+ | `npm --version` |

### Vérification Rapide

```bash
# Vérifier toutes les versions
java --version
mvn --version
docker --version
docker compose version
node --version
npm --version
```

---

## 🚀 LANCEMENT DE L'INFRASTRUCTURE

### Étape 1: Cloner le Projet

```bash
# Cloner le dépôt
git clone https://github.com/Amal356/API-REST-Spring-Boot-4-App-mobile.git

# Entrer dans le répertoire
cd API-REST-Spring-Boot-4-App-mobile

# Basculer sur la branche version-4
git checkout version-4
```

### Étape 2: Lancer Docker Compose

```bash
# Lancer tous les services
docker compose up -d --build

# Vérifier que tous les services sont démarrés
docker compose ps
```

**Services attendus:**
- ✅ postgres (port 5432)
- ✅ redis (port 6379)
- ✅ mongodb (port 27017)
- ✅ eureka-server (port 8761)
- ✅ etudiant-service (port 8081)
- ✅ grading-service (port 8082)
- ✅ api-gateway (port 8080)
- ✅ frontend (port 3000)
- ✅ auth-service (port 3001)

### Étape 3: Vérifier les Services

```bash
# Vérifier les logs
docker compose logs -f

# Vérifier un service spécifique
docker compose logs etudiant-service

# Vérifier Eureka Dashboard
# Ouvrir dans le navigateur: http://localhost:8761
```

**Attendre que tous les services soient enregistrés dans Eureka (environ 1-2 minutes)**

---

## 🧪 TESTS BACKEND

### 1. Tests Unitaires

**Localisation:** `api-spring-boot/src/test/java/.../unit/`

```bash
# Aller dans le répertoire du service
cd api-spring-boot

# Exécuter les tests unitaires uniquement
./mvnw test -Dtest=EtudiantServiceTest

# Résultat attendu:
# Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```

**Tests exécutés:**
- ✅ `testGetAllEtudiants()`
- ✅ `testGetEtudiantById()`
- ✅ `testCreateEtudiant()`

### 2. Tests d'Intégration

**Localisation:** `api-spring-boot/src/test/java/.../integration/`

```bash
# Exécuter les tests d'intégration
./mvnw test -Dtest=EtudiantIntegrationTest

# Résultat attendu:
# Tests avec Testcontainers (PostgreSQL + Redis)
# Tests run: 5+, Failures: 0, Errors: 0
```

**Tests exécutés:**
- ✅ Tests CRUD complets
- ✅ Tests avec base de données conteneurisée
- ✅ Tests avec cache Redis

### 3. Tests BDD (Cucumber)

**Localisation:** `api-spring-boot/src/test/resources/features/`

```bash
# Exécuter les tests BDD
./mvnw test -Dtest=CucumberTest

# Résultat attendu:
# 3 scenarios (3 passed)
# Tests run: 3, Failures: 0, Errors: 0
```

**Scénarios exécutés:**
- ✅ Créer un étudiant
- ✅ Récupérer tous les étudiants
- ✅ Récupérer un étudiant par ID

### 4. Tous les Tests Backend

```bash
# Exécuter TOUS les tests backend
./mvnw test

# Résultat attendu:
# Tests run: 20+, Failures: 0, Errors: 0, Skipped: 0
```

### 5. Couverture de Code (JaCoCo)

```bash
# Générer le rapport de couverture
./mvnw verify

# Ouvrir le rapport
# Windows:
start target/site/jacoco/index.html

# Linux/Mac:
open target/site/jacoco/index.html
```

**Couverture attendue:** > 80% ✅

### 6. Tests de Stress (Gatling)

**Localisation:** 
- `api-spring-boot/src/test/java/simulations/`
- `api-spring-boot/src/test/scala/simulations/`

```bash
# Exécuter les tests de stress
./mvnw gatling:test

# Résultat attendu:
# 4 simulations exécutées
# Rapports générés dans target/gatling/
```

**Ouvrir le rapport:**
```bash
# Le chemin exact sera affiché dans la console
# Exemple: target/gatling/etudiants imulation-20260504225000/index.html
```

**Scénarios de charge:**
1. ✅ Liste des étudiants (30 users, 20s)
2. ✅ Get étudiant by ID (5 req/s, 30s)
3. ✅ Liste des départements (20 users, 15s)
4. ✅ Workflow complet CRUD (10 users, 30s)

---

## 🌐 TESTS FRONTEND

### 1. Installation des Dépendances

```bash
# Aller dans le répertoire frontend
cd frontend

# Installer les dépendances
npm install
```

### 2. Tests E2E Cypress - Mode Interactif

```bash
# Lancer Cypress en mode interactif
npm run cypress
```

**Actions:**
1. La fenêtre Cypress s'ouvre
2. Sélectionner "E2E Testing"
3. Choisir un navigateur (Chrome recommandé)
4. Cliquer sur "Start E2E Testing"
5. Sélectionner le fichier `etudiants.cy.ts`
6. Observer l'exécution des tests

**Tests exécutés:**
- ✅ Afficher la liste des étudiants
- ✅ Afficher les détails d'un étudiant
- ✅ Naviguer vers les départements
- ✅ Redirection depuis la page d'accueil
- ✅ Afficher la liste des départements
- ✅ Créer un nouveau département
- ✅ Intercepter les appels API
- ✅ Gérer les erreurs réseau

### 3. Tests E2E Cypress - Mode Headless (CI/CD)

```bash
# Lancer Cypress en mode headless
npm run cypress:headless

# Résultat attendu:
# All specs passed!
# 8 tests passed
```

**Avantages du mode headless:**
- ✅ Exécution rapide
- ✅ Pas d'interface graphique
- ✅ Idéal pour CI/CD
- ✅ Génère des vidéos et screenshots

**Résultats:**
- Vidéos: `frontend/cypress/videos/`
- Screenshots: `frontend/cypress/screenshots/`

---

## ✅ VÉRIFICATION DES RÉSULTATS

### 1. Vérification Backend

#### Tests Unitaires
```bash
cd api-spring-boot
./mvnw test -Dtest=EtudiantServiceTest

# ✅ Attendu: Tests run: 3, Failures: 0, Errors: 0
```

#### Tests d'Intégration
```bash
./mvnw test -Dtest=EtudiantIntegrationTest

# ✅ Attendu: Tests run: 5+, Failures: 0, Errors: 0
```

#### Tests BDD
```bash
./mvnw test -Dtest=CucumberTest

# ✅ Attendu: 3 scenarios (3 passed)
```

#### Couverture de Code
```bash
./mvnw verify

# ✅ Attendu: Couverture > 80%
# Ouvrir: target/site/jacoco/index.html
```

#### Tests de Stress
```bash
./mvnw gatling:test

# ✅ Attendu: 4 simulations réussies
# Ouvrir: target/gatling/*/index.html
```

### 2. Vérification Frontend

#### Tests E2E
```bash
cd frontend
npm run cypress:headless

# ✅ Attendu: All specs passed! (8 tests)
```

### 3. Vérification de l'Infrastructure

```bash
# Vérifier que tous les services sont UP
docker compose ps

# ✅ Tous les services doivent être "Up" ou "healthy"
```

```bash
# Vérifier Eureka Dashboard
# Ouvrir: http://localhost:8761

# ✅ Vérifier que les services sont enregistrés:
# - ETUDIANT-SERVICE
# - GRADING-SERVICE
# - API-GATEWAY
```

```bash
# Tester l'API via le Gateway
curl http://localhost:8080/api/etudiants

# ✅ Attendu: Liste des étudiants en JSON
```

```bash
# Tester le Frontend
# Ouvrir: http://localhost:3000

# ✅ Attendu: Page avec liste des étudiants
```

---

## 🔧 TROUBLESHOOTING

### Problème 1: Docker Compose ne démarre pas

**Symptôme:** Erreurs lors de `docker compose up`

**Solutions:**
```bash
# Nettoyer les conteneurs existants
docker compose down -v

# Supprimer les images
docker compose down --rmi all

# Relancer
docker compose up -d --build
```

### Problème 2: Tests Backend échouent

**Symptôme:** Erreurs lors de `./mvnw test`

**Solutions:**
```bash
# Nettoyer le projet
./mvnw clean

# Recompiler
./mvnw compile

# Relancer les tests
./mvnw test
```

### Problème 3: JaCoCo Warnings avec Java 25

**Symptôme:** `Unsupported class file major version 68/69`

**Solution:** Ces avertissements sont non-bloquants. Les tests passent quand même.

**Alternative:**
```bash
# Utiliser Java 21 si disponible
export JAVA_HOME=/path/to/java21
./mvnw test
```

### Problème 4: Cypress ne trouve pas l'application

**Symptôme:** Erreurs de connexion dans Cypress

**Solutions:**
```bash
# Vérifier que le frontend est accessible
curl http://localhost:3000

# Vérifier que l'API Gateway répond
curl http://localhost:8080/api/etudiants

# Attendre que tous les services soient UP
docker compose logs -f
```

### Problème 5: Port déjà utilisé

**Symptôme:** `Port 8080 is already in use`

**Solutions:**
```bash
# Windows: Trouver le processus
netstat -ano | findstr :8080

# Tuer le processus (remplacer PID)
taskkill /PID <PID> /F

# Linux/Mac: Trouver et tuer
lsof -ti:8080 | xargs kill -9
```

### Problème 6: Services ne s'enregistrent pas dans Eureka

**Symptôme:** Services absents du dashboard Eureka

**Solutions:**
```bash
# Attendre 1-2 minutes (délai normal)

# Vérifier les logs du service
docker compose logs etudiant-service

# Redémarrer le service
docker compose restart etudiant-service
```

---

## 📊 RÉSUMÉ DES COMMANDES

### Commandes Essentielles

```bash
# 1. Lancer l'infrastructure
docker compose up -d --build

# 2. Tests Backend (tous)
cd api-spring-boot
./mvnw test

# 3. Couverture de code
./mvnw verify

# 4. Tests de stress
./mvnw gatling:test

# 5. Tests E2E Frontend
cd frontend
npm install
npm run cypress:headless

# 6. Arrêter l'infrastructure
docker compose down
```

### Commandes de Vérification

```bash
# Vérifier les services Docker
docker compose ps

# Vérifier les logs
docker compose logs -f

# Vérifier Eureka
curl http://localhost:8761

# Vérifier API Gateway
curl http://localhost:8080/api/etudiants

# Vérifier Frontend
curl http://localhost:3000
```

---

## 🎯 CHECKLIST D'EXÉCUTION

### Avant de Commencer
- [ ] Java 21+ installé
- [ ] Maven 3.9+ installé
- [ ] Docker installé et démarré
- [ ] Node.js 20+ installé
- [ ] Ports libres (8080, 8081, 8082, 8761, 3000, 5432, 6379, 27017)

### Lancement
- [ ] Projet cloné
- [ ] Branche `version-4` active
- [ ] `docker compose up -d --build` exécuté
- [ ] Tous les services UP (vérifier avec `docker compose ps`)
- [ ] Services enregistrés dans Eureka (http://localhost:8761)

### Tests Backend
- [ ] Tests unitaires passés (`./mvnw test -Dtest=EtudiantServiceTest`)
- [ ] Tests d'intégration passés (`./mvnw test -Dtest=EtudiantIntegrationTest`)
- [ ] Tests BDD passés (`./mvnw test -Dtest=CucumberTest`)
- [ ] Couverture > 80% (`./mvnw verify`)
- [ ] Tests Gatling exécutés (`./mvnw gatling:test`)

### Tests Frontend
- [ ] Dépendances installées (`npm install`)
- [ ] Tests E2E passés (`npm run cypress:headless`)
- [ ] 8 tests Cypress réussis

### Vérification Finale
- [ ] Frontend accessible (http://localhost:3000)
- [ ] API Gateway accessible (http://localhost:8080)
- [ ] Eureka Dashboard accessible (http://localhost:8761)
- [ ] Swagger UI accessible (http://localhost:8081/swagger-ui.html)
- [ ] Tous les tests passent

---

## 📞 SUPPORT

### Documentation

- **README.md** - Documentation principale
- **TESTING.md** - Guide de tests détaillé
- **SPRINT4-JIRA.md** - User Stories Sprint 4
- **RAPPORT_TEST_COMPLET.md** - Rapport de test complet
- **SOUMISSION_TP4.md** - Document de soumission

### Liens Utiles

- **Dépôt GitHub:** https://github.com/Amal356/API-REST-Spring-Boot-4-App-mobile
- **Branche:** version-4
- **Swagger UI:** http://localhost:8081/swagger-ui.html
- **Eureka Dashboard:** http://localhost:8761

---

## ✅ RÉSULTATS ATTENDUS

### Tests Backend
```
[INFO] Tests run: 20+, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Tests Frontend
```
All specs passed!
✓ etudiants.cy.ts (8 tests)
```

### Couverture de Code
```
Line Coverage: 82% (> 80% ✅)
Branch Coverage: 75%
```

### Tests de Stress
```
4 simulations executed successfully
All assertions passed
Response time < 1s (95th percentile)
Success rate > 95%
```

---

*Guide d'exécution généré le 4 Mai 2026*

**Projet:** API REST Spring Boot - Gestion des Étudiants  
**Branche:** version-4  
**Statut:** ✅ PRÊT POUR TESTS
