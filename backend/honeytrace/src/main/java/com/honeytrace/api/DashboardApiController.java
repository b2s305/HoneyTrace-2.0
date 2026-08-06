package com.honeytrace.api;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.honeytrace.ai.AttackAnalysis;
import com.honeytrace.dto.TimelineEvent;
import com.honeytrace.entity.Attacker;
import com.honeytrace.entity.Credential;
import com.honeytrace.service.DashboardService;
@RestController
public class DashboardApiController {

    private final DashboardService dashboardService;

    public DashboardApiController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // ============================
    // Dashboard Statistics
    // ============================

    @GetMapping("/api/dashboard")
    public Map<String, Object> dashboard() {

        Map<String, Object> data = new HashMap<>();

        data.put("attackers", dashboardService.getTotalAttackers());
        data.put("sessions", dashboardService.getTotalSessions());
        data.put("credentials", dashboardService.getTotalCredentials());

        data.put("lowRisk", dashboardService.getLowRiskCount());
        data.put("mediumRisk", dashboardService.getMediumRiskCount());
        data.put("highRisk", dashboardService.getHighRiskCount());

        data.put("topCountry", dashboardService.getTopCountry());
        data.put("topUsername", dashboardService.getTopUsername());
        data.put("topPassword", dashboardService.getTopPassword());

        return data;
    }

    // ============================
    // Attack Map
    // ============================

    @GetMapping("/api/map")
    public List<Attacker> map() {
        return dashboardService.getMapAttackers();
    }

    // ============================
    // AI Analysis
    // ============================

    @GetMapping("/api/analysis")
    public AttackAnalysis analysis() {
        return dashboardService.getLatestAttackAnalysis();
    }

    // ============================
    // Search
    // ============================

    @GetMapping("/api/search")
    public List<Attacker> search(
            @RequestParam(required = false) String keyword) {

        return dashboardService.searchAttackers(keyword);
    }

    // ============================
    // Timeline
    // ============================

    @GetMapping("/api/timeline")
    public List<TimelineEvent> timeline() {

        return dashboardService.getTimeline();

    }

    // ============================
    // All Attackers
    // ============================

    @GetMapping("/api/attackers")
    public List<Attacker> attackers() {

        return dashboardService.getAllAttackers();

    }

    // ============================
    // All Credentials
    // ============================

    @GetMapping("/api/credentials")
    public List<Credential> credentials() {

        return dashboardService.getAllCredentials();

    }


//============================
//All Commands
//============================

}