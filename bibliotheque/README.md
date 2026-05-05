# Gestion d'une Bibliothèque - Master STR (FST Tunis)

Ce projet est une application web complète de gestion de bibliothèque développée avec **Spring Boot 3**, **Thymeleaf**, et **MySQL**.

## 🚀 Fonctionnalités

- **Tableau de Bord** : Statistiques en temps réel (livres, membres, emprunts, retards).
- **Gestion du Catalogue** : CRUD complet des livres avec recherche multicritères et pagination.
- **Gestion des Membres** : Inscription et suivi des membres de la bibliothèque.
- **Système d'Emprunt** : 
    - Création d'emprunts avec vérification de disponibilité.
    - Gestion des retours simplifiée.
    - Mise à jour automatique des statuts de retard (Scheduled Task).
- **Sécurité** :
    - Authentification basée sur les rôles (ADMIN et USER).
    - Contrôle d'accès granulaire sur les fonctionnalités CRUD.
- **Export** : Exportation des emprunts en cours au format CSV.
- **Design** : Interface responsive moderne avec Bootstrap 5 et icônes Bootstrap.

## 🛠 Stack Technique

- **Backend** : Java 17, Spring Boot 3.2.5, Spring Security, Spring Data JPA.
- **Frontend** : Thymeleaf 3, Bootstrap 5.3, Bootstrap Icons.
- **Base de données** : MySQL 8.
- **Build Tool** : Maven.

## 📋 Prérequis

- **Java 17** ou supérieur.
- **Maven** installé.
- **MySQL 8** (Créer une base de données nommée `bibliotheque_db`).

## ⚙️ Installation

1. Cloner le projet ou extraire les fichiers.
2. Configurer les accès MySQL dans `src/main/resources/application.properties` :
   ```properties
   spring.datasource.username=VOTRE_USERNAME
   spring.datasource.password=VOTRE_PASSWORD
   ```
3. Lancer l'application :
   ```bash
   mvn spring-boot:run
   ```
4. Accéder à l'application sur : `http://localhost:8080`

## 🔐 Comptes de Test

| Rôle | Utilisateur | Mot de passe | Permissions |
| :--- | :--- | :--- | :--- |
| **ADMIN** | `admin` | `admin123` | Accès complet (CRUD) |
| **USER** | `user` | `user123` | Consultation uniquement |

## 📂 Structure des Packages

- `com.fst.bibliotheque` : Racine
    - `.entity` : Entités JPA (Livre, Membre, Emprunt, Statut)
    - `.repository` : Interfaces Spring Data JPA
    - `.service` : Logique métier et calculs
    - `.controller` : Contrôleurs Spring MVC
    - `.config` : Configuration Sécurité (Spring Security)

---
*Projet réalisé dans le cadre du Master STR à la Faculté des Sciences de Tunis.*
