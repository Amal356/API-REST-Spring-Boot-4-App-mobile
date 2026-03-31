# Projet Étudiants

## Structure
- `etudiantsapi/` : API REST Spring Boot 4
- `mobile_app/` : Application Flutter
- `react_native_app/` : Application React Native

## Lancer l'API + Base de données PostgreSQL
```bash
cd etudiantsapi
docker compose up --build
```
API accessible sur : http://localhost:8081/api/etudiants

## Lancer l'app Flutter
```bash
cd mobile_app
flutter run -d chrome --web-browser-flag "--disable-web-security"
```

## Lancer l'app React Native
```bash
cd react_native_app
npx expo start --web
```