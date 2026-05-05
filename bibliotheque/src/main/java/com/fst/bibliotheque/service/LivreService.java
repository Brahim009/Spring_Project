package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.repository.LivreRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class LivreService {

    private final LivreRepository livreRepository;

    public LivreService(LivreRepository livreRepository) {
        this.livreRepository = livreRepository;
    }

    public Page<Livre> findAll(Pageable pageable) {
        return livreRepository.findAll(pageable);
    }

    public Optional<Livre> findById(Long id) {
        return livreRepository.findById(id);
    }

    public Livre save(Livre livre) {
        return livreRepository.save(livre);
    }

    public void delete(Long id) {
        livreRepository.deleteById(id);
    }

    public Page<Livre> rechercher(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return livreRepository.findAll(pageable);
        }
        return livreRepository.rechercher(keyword.trim(), pageable);
    }

    public boolean estDisponible(Long livreId) {
        return livreRepository.findById(livreId)
                .map(l -> l.getQuantiteDisponible() > 0)
                .orElse(false);
    }

    public void decrementerDisponible(Livre livre) {
        if (livre.getQuantiteDisponible() <= 0) {
            throw new IllegalStateException("Aucun exemplaire disponible pour : " + livre.getTitre());
        }
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() - 1);
        livreRepository.save(livre);
    }

    public void incrementerDisponible(Livre livre) {
        livre.setQuantiteDisponible(livre.getQuantiteDisponible() + 1);
        livreRepository.save(livre);
    }

    public long count() {
        return livreRepository.count();
    }
}
