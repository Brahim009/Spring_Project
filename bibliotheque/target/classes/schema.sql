-- Schema SQL pour la base de données de la bibliothèque

-- Les tables sont normalement créées automatiquement par Hibernate (update)
-- mais ce fichier sert de référence DDL.

DROP TABLE IF EXISTS emprunts;
DROP TABLE IF EXISTS livres;
DROP TABLE IF EXISTS membres;

CREATE TABLE livres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255),
    isbn VARCHAR(50) UNIQUE,
    categorie VARCHAR(100),
    quantite_total INT NOT NULL,
    quantite_disponible INT NOT NULL
);

CREATE TABLE membres (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    date_inscription DATE NOT NULL,
    actif BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE emprunts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    livre_id BIGINT NOT NULL,
    membre_id BIGINT NOT NULL,
    date_emprunt DATE NOT NULL,
    date_retour_prevue DATE NOT NULL,
    date_retour_effective DATE,
    statut VARCHAR(20) NOT NULL,
    FOREIGN KEY (livre_id) REFERENCES livres(id),
    FOREIGN KEY (membre_id) REFERENCES membres(id)
);
