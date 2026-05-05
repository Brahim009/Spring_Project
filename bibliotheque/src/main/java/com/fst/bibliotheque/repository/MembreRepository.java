package com.fst.bibliotheque.repository;

import com.fst.bibliotheque.entity.Membre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MembreRepository extends JpaRepository<Membre, Long> {

    Optional<Membre> findByEmailIgnoreCase(String email);

    List<Membre> findByActifTrue();
}
