# Checklist de Vérification Finale - Projet Bibliothèque FST

Avant la soutenance de votre Master STR, assurez-vous de valider les points suivants :

## 1. Configuration & Lancement
- [ ] La base de données `bibliotheque_db` est créée sous MySQL.
- [ ] Le `pom.xml` ne contient aucune erreur de dépendance (exécution de `mvn clean compile`).
- [ ] L'application démarre sans erreur (`mvn spring-boot:run`).
- [ ] Les tables sont bien créées et alimentées par `data.sql` au premier lancement.

## 2. Authentification & Autorisation
- [ ] Connexion avec `admin/admin123` réussie (Rôle ADMIN).
- [ ] Connexion avec `user/user123` réussie (Rôle USER).
- [ ] Déconnexion fonctionnelle.
- [ ] L'utilisateur `user` ne peut PAS accéder aux formulaires d'ajout/modification (Erreur 403 affichée ou lien masqué).

## 3. Gestion des Livres (CRUD)
- [ ] Liste des livres s'affiche avec pagination.
- [ ] La recherche par mot-clé (titre/auteur/catégorie) fonctionne.
- [ ] Ajout d'un nouveau livre fonctionnel avec validation (titre obligatoire).
- [ ] Modification et Suppression d'un livre opérationnelles.

## 4. Gestion des Membres
- [ ] Liste des membres affichée correctement.
- [ ] Inscription d'un nouveau membre (vérifier le format de l'email).
- [ ] Désactivation d'un membre (passer `actif` à false).

## 5. Système d'Emprunt
- [ ] Créer un emprunt : vérifier que seuls les livres avec `quantiteDisponible > 0` sont sélectionnables.
- [ ] Créer un emprunt : vérifier que la quantité disponible diminue de 1.
- [ ] Enregistrer un retour : vérifier que la quantité disponible augmente de 1 et le statut passe à `RENDU`.
- [ ] Vérifier que les badges de couleur s'affichent (Bleu: EN_COURS, Vert: RENDU, Rouge: EN_RETARD).

## 6. Dashboard & Services
- [ ] Les compteurs sur le dashboard sont exacts.
- [ ] L'export CSV télécharge bien un fichier lisible avec Excel.
- [ ] Vérifier le déclenchement de la mise à jour des retards (logs au démarrage).

## 7. Design & UI
- [ ] L'interface est responsive (test sur mobile/tablette via navigateur).
- [ ] Les icônes Bootstrap s'affichent correctement.
- [ ] Les pages d'erreurs (404, 403, 500) sont personnalisées et propres.
