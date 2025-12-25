# SchoolAdmin API

## Description

SchoolAdmin API est une API REST développée avec Spring Boot pour la gestion administrative d'une université. Elle permet de gérer les étudiants, les professeurs, les cours, les évaluations, les emplois du temps, les annonces, et bien plus encore. L'API utilise JWT pour l'authentification et PostgreSQL comme base de données.

## Fonctionnalités

- **Gestion des utilisateurs** : Authentification et autorisation avec JWT.
- **Gestion des étudiants** : Inscription, admission, documents, notes.
- **Gestion des professeurs** : Profils et affectations.
- **Gestion des cours et modules** : Création et organisation des cours.
- **Évaluations et notes** : Système d'évaluation des étudiants.
- **Emplois du temps** : Gestion des horaires et séances.
- **Annonces** : Publication d'annonces pour la communauté universitaire.
- **Statistiques** : Rapports et analyses.
- **Sécurité** : Authentification sécurisée et gestion des rôles.
- **Upload de fichiers** : Gestion des documents et fichiers.

## Prérequis

- Java 17
- Maven 3.6+
- PostgreSQL
- Docker (optionnel pour la conteneurisation)

## Installation

1. Clonez le dépôt :
   ```bash
   git clone <url-du-depot>
   cd SchoolAdmin_API
   ```

2. Configurez la base de données PostgreSQL et définissez les variables d'environnement :
   - `SPRING_DATASOURCE_URL` : URL de la base de données (ex: jdbc:postgresql://localhost:5432/schooladmin)
   - `SPRING_DATASOURCE_USERNAME` : Nom d'utilisateur de la base de données
   - `SPRING_DATASOURCE_PASSWORD` : Mot de passe de la base de données

3. Installez les dépendances :
   ```bash
   mvn clean install
   ```

## Configuration

Modifiez le fichier `src/main/resources/application.properties` pour ajuster les paramètres :

- Port du serveur : `server.port=9000`
- Répertoire d'upload : `file.upload-dir=uploads/`
- Expiration du refresh token JWT : `jwt.refresh.expiration=604800` (en secondes)

## Exécution

Pour lancer l'application en mode développement :
```bash
mvn spring-boot:run
```

L'API sera accessible sur `http://localhost:9000`.

## Utilisation de Docker

Pour construire et exécuter l'application avec Docker :

1. Construisez l'image :
   ```bash
   docker build -t schooladmin-api .
   ```

2. Exécutez le conteneur :
   ```bash
   docker run -p 9000:9000 -e SPRING_DATASOURCE_URL=<url> -e SPRING_DATASOURCE_USERNAME=<user> -e SPRING_DATASOURCE_PASSWORD=<pass> schooladmin-api
   ```

## Documentation de l'API

L'API expose plusieurs endpoints REST. Voici quelques exemples :

- `POST /api/auth/login` : Connexion
- `GET /api/etudiants` : Liste des étudiants
- `POST /api/professeurs` : Ajouter un professeur
- `GET /api/cours` : Liste des cours
- `GET /api/admin/activities/recent` : Activités récentes

Pour une documentation complète, consultez les contrôleurs dans le code source ou utilisez un outil comme Swagger si configuré.

## Tests

Pour exécuter les tests :
```bash
mvn test
```

## Contribution

1. Forkez le projet.
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/nouvelle-fonction`).
3. Commitez vos changements (`git commit -am 'Ajout de nouvelle fonctionnalité'`).
4. Poussez vers la branche (`git push origin feature/nouvelle-fonction`).
5. Ouvrez une Pull Request.

