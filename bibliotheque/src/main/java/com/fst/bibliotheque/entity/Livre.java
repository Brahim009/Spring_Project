package com.fst.bibliotheque.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "livres")
public class Livre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre est obligatoire")
    @Column(nullable = false)
    private String titre;

    private String auteur;

    @Column(unique = true)
    private String isbn;

    private String categorie;

    @Min(value = 0, message = "La quantité totale ne peut pas être négative")
    @Column(nullable = false)
    private int quantiteTotal;

    @Min(value = 0, message = "La quantité disponible ne peut pas être négative")
    @Column(nullable = false)
    private int quantiteDisponible;

    @OneToMany(mappedBy = "livre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emprunt> emprunts;

    // Constructeurs
    public Livre() {}

    public Livre(Long id, String titre, String auteur, String isbn, String categorie, int quantiteTotal, int quantiteDisponible) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.isbn = isbn;
        this.categorie = categorie;
        this.quantiteTotal = quantiteTotal;
        this.quantiteDisponible = quantiteDisponible;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public int getQuantiteTotal() { return quantiteTotal; }
    public void setQuantiteTotal(int quantiteTotal) { this.quantiteTotal = quantiteTotal; }

    public int getQuantiteDisponible() { return quantiteDisponible; }
    public void setQuantiteDisponible(int quantiteDisponible) { this.quantiteDisponible = quantiteDisponible; }

    public List<Emprunt> getEmprunts() { return emprunts; }
    public void setEmprunts(List<Emprunt> emprunts) { this.emprunts = emprunts; }

    @Override
    public String toString() {
        return "Livre{" + "id=" + id + ", titre='" + titre + '\'' + ", auteur='" + auteur + '\'' + '}';
    }
}
