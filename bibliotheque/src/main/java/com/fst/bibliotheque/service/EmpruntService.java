package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.repository.EmpruntRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpruntService {

    private static final Logger log = LoggerFactory.getLogger(EmpruntService.class);

    private final EmpruntRepository empruntRepository;
    private final LivreService livreService;
    private final MembreService membreService;

    public EmpruntService(EmpruntRepository empruntRepository, LivreService livreService, MembreService membreService) {
        this.empruntRepository = empruntRepository;
        this.livreService = livreService;
        this.membreService = membreService;
    }

    public Emprunt creerEmprunt(Long livreId, Long membreId, LocalDate dateRetourPrevue) {
        Livre livre = livreService.findById(livreId)
                .orElseThrow(() -> new IllegalArgumentException("Livre introuvable : " + livreId));

        Membre membre = membreService.findById(membreId)
                .orElseThrow(() -> new IllegalArgumentException("Membre introuvable : " + membreId));

        if (!membre.isActif()) {
            throw new IllegalStateException("Le membre " + membre.getNomComplet() + " est inactif.");
        }

        livreService.decrementerDisponible(livre);

        Emprunt emprunt = new Emprunt();
        emprunt.setLivre(livre);
        emprunt.setMembre(membre);
        emprunt.setDateEmprunt(LocalDate.now());
        emprunt.setDateRetourPrevue(dateRetourPrevue);
        emprunt.setStatut(StatutEmprunt.EN_COURS);

        return empruntRepository.save(emprunt);
    }

    public Emprunt enregistrerRetour(Long empruntId) {
        Emprunt emprunt = empruntRepository.findById(empruntId)
                .orElseThrow(() -> new IllegalArgumentException("Emprunt introuvable : " + empruntId));

        if (emprunt.getStatut() == StatutEmprunt.RENDU) {
            throw new IllegalStateException("Cet emprunt a déjà été retourné.");
        }

        livreService.incrementerDisponible(emprunt.getLivre());

        emprunt.setDateRetourEffective(LocalDate.now());
        emprunt.setStatut(StatutEmprunt.RENDU);

        return empruntRepository.save(emprunt);
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *")
    public void mettreAJourStatutsEnRetard() {
        LocalDate today = LocalDate.now();
        List<Emprunt> enCours = empruntRepository.findByStatut(StatutEmprunt.EN_COURS);
        int count = 0;
        for (Emprunt e : enCours) {
            if (e.getDateRetourPrevue().isBefore(today)) {
                e.setStatut(StatutEmprunt.EN_RETARD);
                empruntRepository.save(e);
                count++;
            }
        }
        if (count > 0) {
            log.info("[Bibliothèque] {} emprunt(s) passé(s) EN_RETARD.", count);
        }
    }

    public Page<Emprunt> findAll(Pageable pageable) {
        return empruntRepository.findAll(pageable);
    }

    public Page<Emprunt> findByStatut(StatutEmprunt statut, Pageable pageable) {
        return empruntRepository.findByStatut(statut, pageable);
    }

    public List<Emprunt> findByStatut(StatutEmprunt statut) {
        return empruntRepository.findByStatut(statut);
    }

    public Optional<Emprunt> findById(Long id) {
        return empruntRepository.findById(id);
    }

    public long countByStatut(StatutEmprunt statut) {
        return empruntRepository.countByStatut(statut);
    }

    public long count() {
        return empruntRepository.count();
    }
}
