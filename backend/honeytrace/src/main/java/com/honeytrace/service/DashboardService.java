package com.honeytrace.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.honeytrace.ai.AttackAnalysis;
import com.honeytrace.ai.AttackAnalyzer;
import com.honeytrace.dto.TimelineEvent;
import com.honeytrace.entity.Attacker;
import com.honeytrace.entity.Credential;
import com.honeytrace.repository.AttackerRepository;
import com.honeytrace.repository.CredentialRepository;
import com.honeytrace.repository.SessionRepository;

@Service
public class DashboardService {

    private final AttackerRepository attackerRepository;
    private final CredentialRepository credentialRepository;
    private final SessionRepository sessionRepository;
    private final AttackAnalyzer attackAnalyzer;
  

    public DashboardService(
            AttackerRepository attackerRepository,
            CredentialRepository credentialRepository,
            SessionRepository sessionRepository,
            AttackAnalyzer attackAnalyzer) {

        this.attackerRepository = attackerRepository;
        this.credentialRepository = credentialRepository;
        this.sessionRepository = sessionRepository;
        this.attackAnalyzer = attackAnalyzer;
    }
    // ==========================================
    // Dashboard Statistics
    // ==========================================

    public long getTotalAttackers() {
        return attackerRepository.count();
    }

    public long getTotalCredentials() {
        return credentialRepository.count();
    }

    public long getTotalSessions() {
        return sessionRepository.count();
    }

    // ==========================================
    // Recent Data
    // ==========================================

    public List<Attacker> getRecentAttackers() {
        return attackerRepository.findTop10ByOrderByIdDesc();
    }

    public List<Credential> getRecentCredentials() {
        return credentialRepository.findTop10ByOrderByIdDesc();
    }

    // ==========================================
    // Attack Map
    // ==========================================

    public List<Attacker> getMapAttackers() {
        return attackerRepository.findAllByLatitudeIsNotNullAndLongitudeIsNotNull();
    }

    // ==========================================
    // Threat Level
    // ==========================================

    public String getThreatLevel(Integer riskScore) {

        if (riskScore == null)
            return "UNKNOWN";

        if (riskScore >= 70)
            return "HIGH";

        if (riskScore >= 30)
            return "MEDIUM";

        return "LOW";
    }

    // ==========================================
    // Threat Intelligence
    // ==========================================

    public long getLowRiskCount() {
        return attackerRepository.countByRiskScoreLessThan(30);
    }

    public long getMediumRiskCount() {
        return attackerRepository.countByRiskScoreBetween(30, 69);
    }

    public long getHighRiskCount() {
        return attackerRepository.countByRiskScoreGreaterThanEqual(70);
    }

    public String getTopCountry() {

        List<String> countries = attackerRepository.getTopCountries();

        if (countries.isEmpty()) {
            return "Unknown";
        }

        return countries.get(0);
    }

    public String getTopUsername() {

        String username = credentialRepository.getTopUsername();

        return username != null ? username : "-";
    }

    public String getTopPassword() {

        String password = credentialRepository.getTopPassword();

        return password != null ? password : "-";
    }

    // ==========================================
    // AI Analysis
    // ==========================================

    public AttackAnalysis getLatestAttackAnalysis() {

        List<Credential> credentials =
                credentialRepository.findTop10ByOrderByIdDesc();

        if (credentials.isEmpty()) {

            AttackAnalysis analysis = new AttackAnalysis();

            analysis.setAttackType("No Attack");
            analysis.setThreatLevel("LOW");
            analysis.setConfidence(0);
            analysis.setRecommendation("No attacks detected.");

            analysis.setTopCountry("Unknown");
            analysis.setTopIp("-");
            analysis.setHighRiskCount(0);
            analysis.setTotalAttackers(0);

            return analysis;
        }

        Credential credential = credentials.get(0);

        Attacker attacker = credential.getSession().getAttacker();

        AttackAnalysis analysis = attackAnalyzer.analyze(

                credential.getUsername(),
                credential.getPassword(),
                attacker.getRiskScore()

        );

        analysis.setTopCountry(getTopCountry());
        analysis.setTopIp(attacker.getIpAddress());
        analysis.setHighRiskCount((int) getHighRiskCount());
        analysis.setTotalAttackers(getTotalAttackers());

        return analysis;
    }

    // ==========================================
    // Search
    // ==========================================

    public List<Attacker> searchAttackers(String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return attackerRepository.findTop10ByOrderByIdDesc();
        }

        return attackerRepository.findByIpAddressContainingIgnoreCase(keyword);
    }

    // ==========================================
    // All Attackers
    // ==========================================

    public List<Attacker> getAllAttackers() {
        return attackerRepository.findAll();
    }

    // ==========================================
    // Timeline
    // ==========================================

    public List<TimelineEvent> getTimeline() {

        List<TimelineEvent> events = new ArrayList<>();

        List<Attacker> attackers =
                attackerRepository.findTop10ByOrderByLastSeenDesc();

        for (Attacker attacker : attackers) {

            String message = "SSH Attack from " + attacker.getIpAddress();

            if (attacker.getCountry() != null) {
                message += " (" + attacker.getCountry() + ")";
            }

            events.add(
                    new TimelineEvent(
                            attacker.getLastSeen()
                                    .toLocalTime()
                                    .withNano(0)
                                    .toString(),
                            message
                    )
            );
        }

        return events;
    }
 // ==========================================
 // All Credentials
 // ==========================================

 public List<Credential> getAllCredentials() {

     return credentialRepository.findAll();

 }


//==========================================
//All Commands
//==========================================


}