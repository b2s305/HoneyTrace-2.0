package com.honeytrace.ai;

import org.springframework.stereotype.Component;

@Component
public class AttackAnalyzer {

    public AttackAnalysis analyze(String username,
                                  String password,
                                  Integer riskScore) {

        if (riskScore == null)
            riskScore = 0;

        username = username == null ? "" : username.toLowerCase().trim();
        password = password == null ? "" : password.toLowerCase().trim();

        String attackType;
        String threatLevel;
        int confidence;
        String recommendation;

        // Default values
        attackType = "Suspicious Login";
        threatLevel = "LOW";
        confidence = 70;
        recommendation = "Continue monitoring attacker activity.";

        // Weak password attack
        if (password.equals("123456")
                || password.equals("12345")
                || password.equals("password")
                || password.equals("admin")
                || password.equals("root")) {

            attackType = "Weak Password Attack";
            threatLevel = "HIGH";
            confidence = 96;
            recommendation =
                    "Force password reset, enforce strong password policy and enable MFA.";
        }

        // Administrator account attack
        else if (username.equals("admin")
                || username.equals("administrator")
                || username.equals("root")) {

            attackType = "Administrator Account Attack";
            threatLevel = "HIGH";
            confidence = 94;
            recommendation =
                    "Immediately block the source IP and review administrator account activity.";
        }

        // Credential Stuffing
        else if (riskScore >= 80) {

            attackType = "Credential Stuffing";
            threatLevel = "CRITICAL";
            confidence = 99;
            recommendation =
                    "Block attacker immediately. Investigate compromised credentials and enable account lockout.";
        }

        // SSH Brute Force
        else if (riskScore >= 70) {

            attackType = "SSH Brute Force";
            threatLevel = "HIGH";
            confidence = 93;
            recommendation =
                    "Block repeated SSH attempts and enable intrusion prevention.";
        }

        // Password Spraying
        else if (riskScore >= 40) {

            attackType = "Password Spraying";
            threatLevel = "MEDIUM";
            confidence = 84;
            recommendation =
                    "Monitor authentication logs and increase login protection.";
        }

        // Reconnaissance
        else if (riskScore >= 20) {

            attackType = "Reconnaissance";
            threatLevel = "LOW";
            confidence = 76;
            recommendation =
                    "Monitor network traffic for additional suspicious behaviour.";
        }

        AttackAnalysis analysis = new AttackAnalysis();

        analysis.setAttackType(attackType);
        analysis.setThreatLevel(threatLevel);
        analysis.setConfidence(confidence);
        analysis.setRecommendation(recommendation);

        return analysis;
    }

}