# 🧪 PLAN DE TEST COMPLET - Projet API REST Spring Boot

## 📋 Table des Matières
1. [Tests Partie 1 - API REST de Base](#partie-1)
2. [Tests Partie 2 - Tests BDD, Cache Redis, Kubernetes](#partie-2)
3. [Tests Partie 3 - Microservices (Eureka, Gateway, Grading)](#partie-3)
4. [Tests Partie 4 - Tests E2E et Performance](#partie-4)
5. [Tests d'Intégration Globale](#tests-integration-globale)

---

## 🎯 PARTIE 1 - API REST de Base

### ✅ Checklist Partie 1
- [ ] API Spring Boot démarre correctement
- [ ] Base de données PostgreSQL fonctionne
- [ ] Endpoints CRUD fonctionnent
- [ ] Docker Compose lance tous les services
- [ ] Application mobile Flutter se connecte à l'API
- [ ] Application React Native se connecte à l'API

### 🔧 Tests à Exécuter

#### 1.1 Démarrage de l'API Spring Boot
```bash
cd api-spring-boot
mvn clean install
mvn spring-boot:run
```
**Résultat attendu** : Application démarre sur port 8081

#### 1.2 Test Docker Compose
```bash
docker compose up -d
docker compose ps
```
**Résultat attendu** : 3 services actifs (postgres, redis, api)

#### 1.3 Test des Endpoints REST
```bash
# GET - Liste des étudiants
curl http://localhost:8081/api/etudiants

# POST - Créer un étudiant
curl -X POST http://localhost:8081/api/etudiants \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean",
    "dateNaissance": "2000-01-15",
    "email": "jean.dupont@test.com",
    "anneePremiereInscription": 2020
  }'

# GET - Récupérer un étudiant par ID
curl http://localhost:8081/api/etudiants/1

# PUT - Modifier un étudiant
curl -X PUT http://localhost:8081/api/etudiants/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Dupont",
    "prenom": "Jean-Pierre",
    "dateNaissance": "2000-01-15",
    "email": "jp.dupont@test.com",
    "anneePremiereInscription": 2020
  }'

# DELETE - Supprimer un étudiant
curl -X DELETE http://localhost:8081/api/etudiants/1
```

#### 1.4 Test Application Mobile Flutter
```bash
cd mobile-app
flutter pub get
flutter run
```
**Actions manuelles** :
- Vérifier l'affichage de la liste des étudiants
- Tester l'ajout d'un étudiant
- Tester la modification
- Tester la suppression

#### 1.5 Test Application React Native
```bash
cd react_native_app
npm install
npx expo start
```
**Actions manuelles** :
- Scanner le QR code avec Expo Go
- Vérifier l'affichage de la liste
- Tester les opérations CRUD

---

## 🎯 PARTIE 2 - Tests BDD, Cache Redis, Kubernetes

### ✅ Checklist Partie 2
- [ ] Tests BDD Cucumber passent
- [ ] Cache Redis fonctionne
- [ ] Page index.html affiche les étudiants
- [ ] Documentation Swagger accessible
- [ ] Déploiement Kubernetes fonctionne
- [ ] Tests unitaires passent (couverture > 80%)

### 🔧 Tests à Exécuter

#### 2.1 Tests BDD Cucumber
```bash
cd api-spring-boot
mvn test -Dtest=CucumberTest
```
**Résultat attendu** : 3 scénarios passent (calcul d'âge)

#### 2.2 Tests Unitaires et Couverture
```bash
mvn clean test
mvn jacoco:report
```
**Résultat attendu** : Couverture > 80%
**Rapport** : `target/site/jacoco/index.html`

#### 2.3 Test Cache Redis
```bash
# Démarrer Redis
docker compose up -d redis

# Tester via l'API (2 appels identiques)
curl http://localhost:8081/api/etudiants/1
curl http://localhost:8081/api/etudiants/1

# Vérifier les logs pour voir "Cache hit"
docker compose logs api-spring-boot | grep -i cache
```

#### 2.4 Test Page index.html
```bash
# Ouvrir dans le navigateur
open http://localhost:8081/index.html
```
**Résultat attendu** : Liste des étudiants affichée dynamiquement

#### 2.5 Test Documentation Swagger
```bash
open http://localhost:8081/swagger-ui.html
```
**Actions manuelles** :
- Vérifier que tous les endpoints sont documentés
- Tester un endpoint via Swagger UI

#### 2.6 Test Déploiement Kubernetes
```bash
cd k8s

# Appliquer les manifests
kubectl apply -f postgres-deployment.yaml
kubectl apply -f etudiant-deployment.yaml

# Vérifier les déploiements
kubectl get deployments
kubectl get services
kubectl get pods

# Tester l'accès
kubectl port-forward svc/etudiant-service 8080:8080
curl http://localhost:8080/api/etudiants
```

---

## 🎯 PARTIE 3 - Microservices (Eureka, Gateway, Grading)

### ✅ Checklist Partie 3
- [ ] Eureka Server démarre et affiche le dashboard
- [ ] API Gateway route correctement les requêtes
- [ ] Grading Service calcule les moyennes
- [ ] Frontend Next.js affiche les données
- [ ] Tous les services s'enregistrent dans Eureka
- [ ] Communication Feign fonctionne

### 🔧 Tests à Exécuter

#### 3.1 Démarrage Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```
**Résultat attendu** : Dashboard accessible sur http://localhost:8761
**Vérification** : Aucun service enregistré initialement

#### 3.2 Démarrage API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```
**Résultat attendu** : Gateway démarre sur port 8080
**Vérification Eureka** : Service "API-GATEWAY" apparaît dans le dashboard

#### 3.3 Démarrage Grading Service
```bash
cd grading-service
mvn spring-boot:run
```
**Résultat attendu** : Service démarre sur port 8082
**Vérification Eureka** : Service "GRADING-SERVICE" apparaît

#### 3.4 Test Routage via Gateway
```bash
# Via Gateway (port 8080)
curl http://localhost:8080/api/etudiants

# Direct (port 8081)
curl http://localhost:8081/api/etudiants

# Grading via Gateway
curl http://localhost:8080/grading/api/grades/etudiant/1
```

#### 3.5 Test Grading Service
```bash
# Créer une note
curl -X POST http://localhost:8082/api/grades \
  -H "Content-Type: application/json" \
  -d '{
    "etudiantId": 1,
    "matiere": "Mathématiques",
    "note": 15.5,
    "coefficient": 2
  }'

# Récupérer les notes d'un étudiant
curl http://localhost:8082/api/grades/etudiant/1

# Calculer la moyenne
curl http://localhost:8082/api/grades/etudiant/1/moyenne
```

#### 3.6 Test Frontend Next.js
```bash
cd frontend
npm install
npm run build
npm start
```
**Actions manuelles** :
- Ouvrir http://localhost:3000
- Vérifier l'affichage de la liste des étudiants
- Tester la navigation
- Vérifier les appels API via DevTools

#### 3.7 Test Communication Feign
```bash
# Vérifier les logs du Grading Service
# Il doit appeler l'API Etudiant via Feign
curl http://localhost:8082/api/grades/etudiant/1/details

# Vérifier dans les logs
cd grading-service
tail -f logs/application.log | grep -i feign
```

---

## 🎯 PARTIE 4 - Tests E2E et Performance

### ✅ Checklist Partie 4
- [ ] Tests E2E Cypress passent
- [ ] Tests de stress Gatling s'exécutent
- [ ] Rapports de tests générés
- [ ] Performance acceptable (< 1s)
- [ ] Taux de succès > 95%

### 🔧 Tests à Exécuter

#### 4.1 Tests E2E Cypress (Mode Interactif)
```bash
cd frontend
npm run cypress
```
**Actions manuelles** :
- Sélectionner le fichier `etudiants.cy.ts`
- Observer l'exécution des 8 scénarios
- Vérifier les captures d'écran en cas d'échec

#### 4.2 Tests E2E Cypress (Mode Headless)
```bash
cd frontend
npm run cypress:headless
```
**Résultat attendu** : 8 tests passent
**Rapport** : `cypress/videos/` et `cypress/screenshots/`

#### 4.3 Tests de Stress Gatling (Simulation Java)
```bash
cd api-spring-boot
mvn gatling:test -Dgatling.simulationClass=simulations.EtudiantSimulation
```
**Résultat attendu** :
- Temps de réponse moyen < 1000ms
- Taux de succès > 95%
**Rapport** : `target/gatling/etudiantsimulation-*/index.html`

#### 4.4 Tests de Stress Gatling (Simulation Scala)
```bash
cd api-spring-boot
mvn gatling:test -Dgatling.simulationClass=simulations.EtudiantSimulationScala
```
**Résultat attendu** : Similaire à la simulation Java
**Rapport** : `target/gatling/etudiantsimulationscala-*/index.html`

#### 4.5 Analyse des Rapports Gatling
**Métriques à vérifier** :
- Nombre total de requêtes
- Temps de réponse (min, max, moyenne, percentiles)
- Taux de succès/échec
- Requêtes par seconde

---

## 🎯 TESTS D'INTÉGRATION GLOBALE

### ✅ Checklist Intégration Globale
- [ ] Tous les services démarrent ensemble
- [ ] Communication inter-services fonctionne
- [ ] Frontend communique avec le backend via Gateway
- [ ] Base de données persiste les données
- [ ] Cache améliore les performances
- [ ] Logs et monitoring fonctionnent

### 🔧 Tests à Exécuter

#### 5.1 Démarrage Complet avec Docker Compose
```bash
# Démarrer tous les services
docker compose up -d

# Vérifier l'état
docker compose ps

# Vérifier les logs
docker compose logs -f
```

#### 5.2 Test Workflow Complet
```bash
# 1. Créer un département
curl -X POST http://localhost:8080/api/departements \
  -H "Content-Type: application/json" \
  -d '{"nom": "Informatique"}'

# 2. Créer un étudiant
curl -X POST http://localhost:8080/api/etudiants \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Martin",
    "prenom": "Sophie",
    "dateNaissance": "2001-05-20",
    "email": "sophie.martin@test.com",
    "anneePremiereInscription": 2021,
    "departementId": 1
  }'

# 3. Ajouter des notes
curl -X POST http://localhost:8080/grading/api/grades \
  -H "Content-Type: application/json" \
  -d '{
    "etudiantId": 1,
    "matiere": "Java",
    "note": 16.0,
    "coefficient": 3
  }'

# 4. Calculer la moyenne
curl http://localhost:8080/grading/api/grades/etudiant/1/moyenne

# 5. Vérifier dans le frontend
open http://localhost:3000
```

#### 5.3 Test de Résilience
```bash
# Arrêter un service
docker compose stop api-spring-boot

# Tester la réponse du Gateway
curl http://localhost:8080/api/etudiants
# Devrait retourner une erreur 503 ou timeout

# Redémarrer le service
docker compose start api-spring-boot

# Attendre la réinscription dans Eureka (30s)
sleep 30

# Retester
curl http://localhost:8080/api/etudiants
# Devrait fonctionner à nouveau
```

#### 5.4 Test de Performance Globale
```bash
# Utiliser Apache Bench pour tester la charge
ab -n 1000 -c 10 http://localhost:8080/api/etudiants

# Ou utiliser wrk
wrk -t4 -c100 -d30s http://localhost:8080/api/etudiants
```

---

## 📊 RAPPORT DE TEST FINAL

### Résumé des Tests
| Partie | Tests | Status | Commentaires |
|--------|-------|--------|--------------|
| Partie 1 | API REST + Docker + Mobile | ⏳ | À tester |
| Partie 2 | BDD + Redis + K8s | ⏳ | À tester |
| Partie 3 | Microservices + Eureka | ⏳ | À tester |
| Partie 4 | E2E + Gatling | ⏳ | À tester |
| Intégration | Workflow complet | ⏳ | À tester |

### Métriques Attendues
- ✅ Couverture de code : > 80%
- ✅ Tests unitaires : 20+ tests
- ✅ Tests BDD : 3 scénarios
- ✅ Tests E2E : 8 scénarios
- ✅ Tests de stress : 4 simulations
- ✅ Temps de réponse : < 1s
- ✅ Taux de succès : > 95%

---

## 🚀 COMMANDES RAPIDES

### Démarrage Rapide
```bash
# Tout démarrer
docker compose up -d

# Vérifier
docker compose ps
curl http://localhost:8080/api/etudiants
open http://localhost:3000
```

### Tests Rapides
```bash
# Tests unitaires
cd api-spring-boot && mvn test

# Tests E2E
cd frontend && npm run cypress:headless

# Tests Gatling
cd api-spring-boot && mvn gatling:test
```

### Nettoyage
```bash
# Arrêter tous les services
docker compose down -v

# Nettoyer les builds
cd api-spring-boot && mvn clean
cd frontend && rm -rf .next node_modules
```

---

**Date de création** : 2026-05-04
**Version** : 1.0
**Auteur** : Équipe Projet
