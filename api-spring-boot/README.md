# Projet Spring Boot - Gestion des Étudiants (Partie 2)

## Objectif Général

Ce projet enrichit l'application Spring Boot de la Partie 1 en ajoutant de la logique métier testée, une interface web légère, une image Docker publiée, un déploiement Kubernetes, et une architecture de microservice plus complète avec cache, gestion des erreurs, documentation et traçabilité Jira.

## Fonctionnalités Implémentées (Partie 2)

Les fonctionnalités suivantes ont été implémentées conformément aux exigences du TP :

### Q1 — Branche Git `version-2`
Le travail a été isolé dans une branche Git dédiée nommée `version-2`.

### Q2 — Méthode `age()` à l'entité `Etudiant`
Une méthode `age()` a été ajoutée à l'entité `Etudiant` pour calculer dynamiquement l'âge à partir de la date de naissance (`dateNaissance`) en utilisant `java.time.Period`. La classe `Etudiant` est correctement annotée avec Lombok (`@Data`, `@Builder`, etc.).

### Q3 — Tests BDD avec Gherkin (Cucumber)
La méthode `age()` est testée en utilisant Cucumber avec JUnit 5. Un fichier `.feature` (`etudiant.feature`) décrit le scénario en langage naturel (Given/When/Then), et les `step definitions` correspondantes sont implémentées en Java (`EtudiantSteps.java`).

### Q4 — Page `index.html` avec appel Fetch JavaScript
Un fichier `index.html` est placé dans `src/main/resources/static/`. Cette page effectue un appel `fetch('/api/etudiants')` pour récupérer et afficher dynamiquement la liste des étudiants dans le DOM, sans utiliser de framework JavaScript.

### Q5 — Création et Publication de l'image Docker
L'image Docker du microservice Spring Boot a été construite et taguée. Les commandes `docker build` et `docker push` sont prêtes à être utilisées avec votre nom d'utilisateur Docker Hub.

### Q6 — Manifests Kubernetes pour K3S
Deux fichiers YAML Kubernetes ont été créés dans le dossier `k8s/` :
- `postgres-deployment.yaml` : Pour le déploiement et le service PostgreSQL.
- `etudiant-deployment.yaml` : Pour le déploiement et le service du microservice étudiant (avec `NodePort`).

### Q7 — Entité `Departement` et mise à jour de `Etudiant`
Une entité JPA `Departement` (`id`, `nom`) a été créée. L'entité `Etudiant` a été mise à jour pour inclure les champs `email`, `anneePremiereInscription` et une relation `@ManyToOne` vers `Departement`. Le schéma de la base de données est mis à jour via `ddl-auto=update`.

### Q8 — Architecture en couches propre
Le projet a été refactorisé en une architecture en couches claire, avec les packages `controller/`, `service/`, `repository/`, `entity/`, `dto/`, `mapper/` et `config/` sous `com.example.etudiants/`. Lombok (`@Data`, `@Builder`, `@RequiredArgsConstructor`) et JDK 25 sont utilisés. Les DTOs et Mappers (MapStruct) sont intégrés pour la conversion entre entités et DTOs.

### Q9 — Requête personnalisée `findByAnneePremiereInscription`
Une méthode `findByAnneePremiereInscription(int annee)` a été ajoutée à `EtudiantRepository` pour filtrer les étudiants par année de première inscription. Cette fonctionnalité est exposée via l'endpoint `GET /api/etudiants?annee={annee}`.

### Q10 — Opérations CRUD complètes
Des endpoints REST standards (GET, POST, PUT, DELETE) ont été implémentés pour les entités `Etudiant` et `Departement`, respectant les codes HTTP appropriés (201 Created, 204 No Content, 404 Not Found).

### Q11 — Gestion des erreurs HTTP standard
Un `@RestControllerAdvice` global (`GlobalExceptionHandler.java`) intercepte les exceptions et retourne des réponses JSON structurées avec les codes HTTP appropriés (404 pour `RuntimeException`, 400 pour `MethodArgumentNotValidException`, 500 pour les erreurs génériques).

### Q12 — Documentation Swagger / OpenAPI
La dépendance `springdoc-openapi-starter-webmvc-ui` a été ajoutée. Les contrôleurs sont annotés avec `@Operation` et `@ApiResponse` pour enrichir la documentation auto-générée, accessible via `http://localhost:8081/swagger-ui.html`.

### Q13 — Cache Redis
Redis est intégré comme couche de cache. La dépendance `spring-boot-starter-data-redis` est ajoutée, `@EnableCaching` est activé, et les méthodes de service utilisent `@Cacheable` pour les lectures et `@CacheEvict` pour les écritures. Un conteneur Redis est inclus dans `docker-compose.yml`.

### Q14 — Projet Jira Scrum
(Cette tâche est une tâche manuelle à effectuer dans Jira. L'implémentation technique est prête pour être liée à un projet Jira.)

## Structure du Projet

```
/projet-etudiants/
├── api-spring-boot/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/etudiants/
│   │   │   │   ├── config/
│   │   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── DepartementController.java
│   │   │   │   │   └── EtudiantController.java
│   │   │   │   ├── dto/
│   │   │   │   │   ├── DepartementDTO.java
│   │   │   │   │   └── EtudiantDTO.java
│   │   │   │   ├── entity/
│   │   │   │   │   ├── Departement.java
│   │   │   │   │   └── Etudiant.java
│   │   │   │   ├── mapper/
│   │   │   │   │   ├── DepartementMapper.java
│   │   │   │   │   └── EtudiantMapper.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── DepartementRepository.java
│   │   │   │   │   └── EtudiantRepository.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── DepartementService.java
│   │   │   │   │   ├── DepartementServiceImpl.java
│   │   │   │   │   ├── EtudiantService.java
│   │   │   │   │   └── EtudiantServiceImpl.java
│   │   │   │   └── EtudiantsApplication.java
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── static/
│   │   │           └── index.html
│   │   └── test/
│   │       ├── java/com/example/etudiants/
│   │       │   ├── steps/
│   │       │   │   └── EtudiantSteps.java
│   │       │   ├── CucumberTest.java
│   │       │   └── EtudiantsApplicationTests.java
│   │       └── resources/
│   │           └── features/
│   │               └── etudiant.feature
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── pom.xml
├── k8s/
│   ├── etudiant-deployment.yaml
│   └── postgres-deployment.yaml
└── README.md
```

## Comment Exécuter le Projet

### Prérequis

*   Java Development Kit (JDK) 25
*   Maven
*   Docker et Docker Compose
*   Un compte Docker Hub (pour la publication de l'image)
*   K3S (pour le déploiement Kubernetes local)

### Exécution Locale avec Docker Compose

1.  **Naviguer vers le répertoire du projet Spring Boot :**
    ```bash
    cd api-spring-boot
    ```

2.  **Construire et démarrer les services (PostgreSQL, Redis, Spring Boot API) :**
    ```bash
    docker compose up -d --build
    ```
    Le flag `--build` est important pour reconstruire l'image Docker de l'API après toute modification du code.

3.  **Vérifier l'état des services :**
    ```bash
    docker compose ps
    ```

4.  **Accéder à l'application :**
    -   **Application web (index.html)** : [http://localhost:8081/index.html](http://localhost:8081/index.html)
    -   **Documentation Swagger/OpenAPI** : [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)

5.  **Arrêter les services :**
    ```bash
    docker compose down
    ```

### Publication de l'image Docker sur Docker Hub

1.  **Naviguer vers le répertoire du projet Spring Boot :**
    ```bash
    cd api-spring-boot
    ```

2.  **Se connecter à Docker Hub :**
    ```bash
    docker login -u <votre-username-dockerhub>
    ```
    Remplacez `<votre-username-dockerhub>` par votre nom d'utilisateur Docker Hub.

3.  **Construire l'image Docker :**
    ```bash
    docker build -t <votre-username-dockerhub>/etudiant-service:1.0 .
    ```

4.  **Pousser l'image sur Docker Hub :**
    ```bash
    docker push <votre-username-dockerhub>/etudiant-service:1.0
    ```

### Déploiement sur Kubernetes (K3S)

1.  **Assurez-vous que K3S est en cours d'exécution sur votre VM locale.**

2.  **Naviguer vers le répertoire `k8s/` :**
    ```bash
    cd k8s
    ```

3.  **Appliquer les manifests Kubernetes :**
    ```bash
    kubectl apply -f postgres-deployment.yaml
    kubectl apply -f etudiant-deployment.yaml
    ```

4.  **Vérifier l'état des déploiements et des services :**
    ```bash
    kubectl get deployments
    kubectl get services
    kubectl get pods
    ```

5.  **Accéder au service étudiant :**
    -   **Via NodePort :** L'application sera accessible sur `http://<adresse-ip-de-votre-vm-k3s>:30080/`.
    -   **Via Port-Forwarding (pour un accès local) :**
        ```bash
        kubectl port-forward svc/etudiant-service 8080:8080
        ```
        Ensuite, accédez à `http://localhost:8080/index.html` ou `http://localhost:8080/swagger-ui.html`.

6.  **Supprimer les déploiements Kubernetes :**
    ```bash
    kubectl delete -f etudiant-deployment.yaml
    kubectl delete -f postgres-deployment.yaml
    ```

## Intégration Jira (Q14)

Cette partie implique la création manuelle d'un projet Scrum dans Jira. La structure attendue est la suivante :

*   **Epic** : Gestion des Étudiants
    *   **Sprint 1** (Couvrant la Partie 1)
        *   US-01 : Lister les étudiants
        *   US-02 : Dockeriser l'application
        *   ...
    *   **Sprint 2** (Couvrant la Partie 2)
        *   US-03 : Méthode `age()` + tests BDD
        *   US-04 : Cache Redis
        *   ...

Il est recommandé de lier chaque commit Git à un ticket Jira en incluant la clé du ticket dans le message de commit (ex. `git commit -m "PROJ-12 : ajout méthode age()"`).
