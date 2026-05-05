package com.fst.bibliotheque.service;

import com.fst.bibliotheque.entity.StatutEmprunt;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final LivreService livreService;
    private final MembreService membreService;
    private final EmpruntService empruntService;

    public DashboardService(LivreService livreService, MembreService membreService, EmpruntService empruntService) {
        this.livreService = livreService;
        this.membreService = membreService;
        this.empruntService = empruntService;
    }

    public long getNbLivres() {
        return livreService.count();
    }

    public long getNbMembres() {
        return membreService.count();
    }

    public long getNbEmpruntsEnCours() {
        return empruntService.countByStatut(StatutEmprunt.EN_COURS);
    }

    public long getNbEnRetard() {
        return empruntService.countByStatut(StatutEmprunt.EN_RETARD);
    }
}
