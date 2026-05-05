package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.entity.Livre;
import com.fst.bibliotheque.service.LivreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/livres")
public class LivreController {

    private final LivreService livreService;

    public LivreController(LivreService livreService) {
        this.livreService = livreService;
    }

    private static final int PAGE_SIZE = 10;

    @GetMapping
    public String liste(Model model,
                        @RequestParam(defaultValue = "") String keyword,
                        @RequestParam(defaultValue = "0") int page) {
        PageRequest pageable = PageRequest.of(page, PAGE_SIZE, Sort.by("titre").ascending());
        Page<Livre> livresPage = livreService.rechercher(keyword, pageable);

        model.addAttribute("livresPage", livresPage);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        return "livres/liste";
    }

    @GetMapping("/nouveau")
    public String nouveauForm(Model model) {
        model.addAttribute("livre", new Livre());
        model.addAttribute("pageTitle", "Ajouter un livre");
        return "livres/form";
    }

    @PostMapping("/nouveau")
    public String nouveauSave(@Valid @ModelAttribute("livre") Livre livre,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Ajouter un livre");
            return "livres/form";
        }
        livreService.save(livre);
        redirectAttributes.addFlashAttribute("successMessage", "Livre ajouté avec succès !");
        return "redirect:/livres";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return livreService.findById(id)
                .map(livre -> {
                    model.addAttribute("livre", livre);
                    model.addAttribute("pageTitle", "Modifier le livre");
                    return "livres/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Livre introuvable.");
                    return "redirect:/livres";
                });
    }

    @PostMapping("/edit/{id}")
    public String editSave(@PathVariable Long id,
                           @Valid @ModelAttribute("livre") Livre livre,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", "Modifier le livre");
            return "livres/form";
        }
        livre.setId(id);
        livreService.save(livre);
        redirectAttributes.addFlashAttribute("successMessage", "Livre modifié avec succès !");
        return "redirect:/livres";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        livreService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Livre supprimé.");
        return "redirect:/livres";
    }
}
