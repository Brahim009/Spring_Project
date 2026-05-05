package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Membre;
import com.fst.bibliotheque.service.MembreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/membres")
public class MembreController {

    private final MembreService membreService;

    public MembreController(MembreService membreService) {
        this.membreService = membreService;
    }

    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String liste(Model model,
                        @RequestParam(defaultValue = "0") int page) {
        PageRequest pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("nom").ascending());
        Page<Membre> membresPage = membreService.findAll(pageable);

        model.addAttribute("membresPage", membresPage);
        model.addAttribute("currentPage", page);
        return "membres/liste";
    }

    @GetMapping("/nouveau")
    public String nouveauForm(Model model) {
        Membre membre = new Membre();
        membre.setDateInscription(LocalDate.now());
        membre.setActif(true);
        model.addAttribute("membre", membre);
        model.addAttribute("pageTitle", "Ajouter un membre");
        return "membres/form";
    }

    @PostMapping("/nouveau")
    public String nouveauSave(@Valid @ModelAttribute("membre") Membre membre,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Ajouter un membre");
            return "membres/form";
        }
        membreService.save(membre);
        redirectAttributes.addFlashAttribute("successMessage", "Membre ajouté avec succès !");
        return "redirect:/membres";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return membreService.findById(id)
                .map(membre -> {
                    model.addAttribute("membre", membre);
                    model.addAttribute("pageTitle", "Modifier le membre");
                    return "membres/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Membre introuvable.");
                    return "redirect:/membres";
                });
    }

    @PostMapping("/edit/{id}")
    public String editSave(@PathVariable Long id,
                           @Valid @ModelAttribute("membre") Membre membre,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Modifier le membre");
            return "membres/form";
        }
        membre.setId(id);
        membreService.save(membre);
        redirectAttributes.addFlashAttribute("successMessage", "Membre modifié avec succès !");
        return "redirect:/membres";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        membreService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Membre supprimé.");
        return "redirect:/membres";
    }
}
