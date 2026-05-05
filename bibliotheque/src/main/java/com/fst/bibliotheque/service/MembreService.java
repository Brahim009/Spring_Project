package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.repository.MembreRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MembreService {

    private final MembreRepository membreRepository;

    public MembreService(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    public Page<Membre> findAll(Pageable pageable) {
        return membreRepository.findAll(pageable);
    }

    public List<Membre> findAll() {
        return membreRepository.findAll();
    }

    public Optional<Membre> findById(Long id) {
        return membreRepository.findById(id);
    }

    public Membre save(Membre membre) {
        return membreRepository.save(membre);
    }

    public void delete(Long id) {
        membreRepository.deleteById(id);
    }

    public List<Membre> findActifs() {
        return membreRepository.findByActifTrue();
    }

    public Optional<Membre> findByEmail(String email) {
        return membreRepository.findByEmailIgnoreCase(email);
    }

    public long count() {
        return membreRepository.count();
    }
}
