-- Jeu de données de test pour la bibliothèque FST

-- 20 Livres
INSERT INTO livres (titre, auteur, isbn, categorie, quantite_total, quantite_disponible) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', '9782070612758', 'Littérature', 5, 5),
('L''Étranger', 'Albert Camus', '9782070360024', 'Littérature', 3, 3),
('1984', 'George Orwell', '9780451524935', 'Roman', 4, 4),
('Java: The Complete Reference', 'Herbert Schildt', '9781260440232', 'Informatique', 2, 2),
('Clean Code', 'Robert C. Martin', '9780132350884', 'Informatique', 3, 3),
('Introduction to Algorithms', 'Thomas H. Cormen', '9780262033848', 'Informatique', 1, 1),
('Design Patterns', 'Erich Gamma', '9780201633610', 'Informatique', 2, 2),
('L''Alchimiste', 'Paulo Coelho', '9782290004449', 'Roman', 6, 6),
('Les Misérables', 'Victor Hugo', '9782253096337', 'Histoire', 2, 2),
('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', '9780062316097', 'Histoire', 4, 4),
('Brief Answers to the Big Questions', 'Stephen Hawking', '9781473695986', 'Physique', 3, 3),
('Deep Learning', 'Ian Goodfellow', '9780262035613', 'Informatique', 2, 2),
('Thinking, Fast and Slow', 'Daniel Kahneman', '9780374275631', 'Psychologie', 3, 3),
('The Art of Computer Programming', 'Donald Knuth', '9780201896831', 'Informatique', 1, 1),
('Atomic Habits', 'James Clear', '9780735211292', 'Développement personnel', 5, 5),
('The Pragmatic Programmer', 'Andrew Hunt', '9780135957059', 'Informatique', 3, 3),
('Effective Java', 'Joshua Bloch', '9780134685991', 'Informatique', 4, 4),
('Building Microservices', 'Sam Newman', '9781491950357', 'Informatique', 2, 2),
('Spring Boot in Action', 'Craig Walls', '9781617292545', 'Informatique', 3, 3),
('Head First Design Patterns', 'Eric Freeman', '9780596007126', 'Informatique', 2, 2);

-- 10 Membres
INSERT INTO membres (nom, prenom, email, date_inscription, actif) VALUES
('Ben Ali', 'Mohamed', 'mohamed.benali@fst.utm.tn', '2024-01-15', 1),
('Trabelsi', 'Amira', 'amira.trabelsi@fst.utm.tn', '2024-01-20', 1),
('Gharbi', 'Sami', 'sami.gharbi@fst.utm.tn', '2024-02-05', 1),
('Ayari', 'Feriel', 'feriel.ayari@fst.utm.tn', '2024-02-12', 1),
('Karray', 'Yassine', 'yassine.karray@fst.utm.tn', '2024-03-01', 1),
('Masmoudi', 'Rania', 'rania.masmoudi@fst.utm.tn', '2024-03-10', 1),
('Haddad', 'Walid', 'walid.haddad@fst.utm.tn', '2024-03-15', 1),
('Sellami', 'Ines', 'ines.sellami@fst.utm.tn', '2024-03-20', 0),
('Hammami', 'Anis', 'anis.hammami@fst.utm.tn', '2024-04-01', 1),
('Bouazizi', 'Leila', 'leila.bouazizi@fst.utm.tn', '2024-04-05', 1);

-- Quelques emprunts de test (Initialisation)
-- Mohamed emprunte Clean Code
INSERT INTO emprunts (livre_id, membre_id, date_emprunt, date_retour_prevue, statut) 
VALUES (5, 1, '2024-04-20', '2024-05-04', 'EN_COURS');
UPDATE livres SET quantite_disponible = quantite_disponible - 1 WHERE id = 5;

-- Amira emprunte Java Complete Reference (En retard)
INSERT INTO emprunts (livre_id, membre_id, date_emprunt, date_retour_prevue, statut) 
VALUES (4, 2, '2024-04-01', '2024-04-15', 'EN_RETARD');
UPDATE livres SET quantite_disponible = quantite_disponible - 1 WHERE id = 4;
