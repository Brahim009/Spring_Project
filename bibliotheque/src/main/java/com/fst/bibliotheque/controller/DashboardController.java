package com.fst.bibliotheque.controller;

import com.fst.bibliotheque.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("nbLivres", dashboardService.getNbLivres());
        model.addAttribute("nbMembres", dashboardService.getNbMembres());
        model.addAttribute("nbEnCours", dashboardService.getNbEmpruntsEnCours());
        model.addAttribute("nbEnRetard", dashboardService.getNbEnRetard());
        return "dashboard";
    }
}
