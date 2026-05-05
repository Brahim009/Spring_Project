package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {

    Page<Livre> findByTitreContainingIgnoreCase(String titre, Pageable pageable);

    List<Livre> findByAuteurContainingIgnoreCase(String auteur);

    List<Livre> findByCategorie(String categorie);

    /**
     * Recherche globale : titre OU auteur OU catégorie contient le mot-clé.
     */
    @Query("SELECT l FROM Livre l WHERE " +
           "LOWER(l.titre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.auteur) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(l.categorie) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Livre> rechercher(@Param("keyword") String keyword, Pageable pageable);
}
