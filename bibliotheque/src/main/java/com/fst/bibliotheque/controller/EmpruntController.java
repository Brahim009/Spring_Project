package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Emprunt;
import com.fst.bibliotheque.entity.StatutEmprunt;
import com.fst.bibliotheque.service.EmpruntService;
import com.fst.bibliotheque.service.LivreService;
import com.fst.bibliotheque.service.MembreService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/emprunts")
public class EmpruntController {

    private final EmpruntService empruntService;
    private final LivreService livreService;
    private final MembreService membreService;

    public EmpruntController(EmpruntService empruntService, LivreService livreService, MembreService membreService) {
        this.empruntService = empruntService;
        this.livreService = livreService;
        this.membreService = membreService;
    }

    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String liste(Model model,
                        @RequestParam(required = false) StatutEmprunt statut,
                        @RequestParam(defaultValue = "0") int page) {

        PageRequest pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("dateEmprunt").descending());
        Page<Emprunt> empruntsPage;

        if (statut != null) {
            empruntsPage = empruntService.findByStatut(statut, pageable);
        } else {
            empruntsPage = empruntService.findAll(pageable);
        }

        model.addAttribute("empruntsPage", empruntsPage);
        model.addAttribute("statutFiltre", statut);
        model.addAttribute("statuts", StatutEmprunt.values());
        model.addAttribute("currentPage", page);
        return "emprunts/liste";
    }

    @GetMapping("/nouveau")
    @PreAuthorize("hasRole('ADMIN')")
    public String nouveauForm(Model model) {
        model.addAttribute("livres", livreService.findAll(PageRequest.of(0, 1000)).getContent());
        model.addAttribute("membres", membreService.findActifs());
        model.addAttribute("dateMin", LocalDate.now().plusDays(1).toString());
        return "emprunts/form";
    }

    @PostMapping("/nouveau")
    @PreAuthorize("hasRole('ADMIN')")
    public String nouveauSave(@RequestParam Long livreId,
                              @RequestParam Long membreId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateRetourPrevue,
                              RedirectAttributes redirectAttributes) {
        try {
            empruntService.creerEmprunt(livreId, membreId, dateRetourPrevue);
            redirectAttributes.addFlashAttribute("successMessage", "Emprunt créé avec succès !");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/emprunts";
    }

    @GetMapping("/retour/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String enregistrerRetour(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            empruntService.enregistrerRetour(id);
            redirectAttributes.addFlashAttribute("successMessage", "Retour enregistré avec succès !");
        } catch (IllegalStateException | IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/emprunts";
    }

    @GetMapping("/export/csv")
    @PreAuthorize("hasRole('ADMIN')")
    public void exportCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"emprunts_en_cours.csv\"");

        PrintWriter writer = response.getWriter();
        writer.write('\ufeff'); // Ajout du BOM UTF-8 pour Excel
        writer.println("Membre,Livre,ISBN,Date Emprunt,Date Retour Prévue,Statut");

        List<Emprunt> emprunts = empruntService.findByStatut(StatutEmprunt.EN_COURS);
        emprunts.addAll(empruntService.findByStatut(StatutEmprunt.EN_RETARD));

        for (Emprunt e : emprunts) {
            writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                    e.getMembre().getNomComplet(),
                    e.getLivre().getTitre(),
                    e.getLivre().getIsbn() != null ? e.getLivre().getIsbn() : "",
                    e.getDateEmprunt(),
                    e.getDateRetourPrevue(),
                    e.getStatut());
        }

        writer.flush();
    }
}
