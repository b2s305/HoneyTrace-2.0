package com.honeytrace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.honeytrace.service.DashboardService;

@Controller
public class HomeController {

    private final DashboardService dashboardService;

    public HomeController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/login")
    public String login() {

        return "login";

    }
    public String home(Model model) {

        // ==========================
        // Dashboard Statistics
        // ==========================

        model.addAttribute("attackers",
                dashboardService.getTotalAttackers());

        model.addAttribute("credentials",
                dashboardService.getTotalCredentials());

        model.addAttribute("sessions",
                dashboardService.getTotalSessions());

        // ==========================
        // Recent Activity
        // ==========================

        model.addAttribute("recentAttackers",
                dashboardService.getRecentAttackers());

        model.addAttribute("recentCredentials",
                dashboardService.getRecentCredentials());

        // ==========================
        // Threat Intelligence
        // ==========================

        model.addAttribute("lowRisk",
                dashboardService.getLowRiskCount());

        model.addAttribute("mediumRisk",
                dashboardService.getMediumRiskCount());

        model.addAttribute("highRisk",
                dashboardService.getHighRiskCount());

        model.addAttribute("topCountry",
                dashboardService.getTopCountry());

        model.addAttribute("topUsername",
                dashboardService.getTopUsername());

        model.addAttribute("topPassword",
                dashboardService.getTopPassword());

        // ==========================
        // AI Analysis
        // ==========================

        model.addAttribute("analysis",
                dashboardService.getLatestAttackAnalysis());

        return "index";
    }

}