package com.honeytrace.ai;

public class AttackAnalysis {

    private String attackType;
    private String threatLevel;
    private int confidence;
    private String recommendation;

    private String topCountry;
    private String topIp;
    private int highRiskCount;
    private long totalAttackers;

    public AttackAnalysis() {
    }

    public AttackAnalysis(String attackType,
                          String threatLevel,
                          int confidence,
                          String recommendation,
                          String topCountry,
                          String topIp,
                          int highRiskCount,
                          long totalAttackers) {

        this.attackType = attackType;
        this.threatLevel = threatLevel;
        this.confidence = confidence;
        this.recommendation = recommendation;
        this.topCountry = topCountry;
        this.topIp = topIp;
        this.highRiskCount = highRiskCount;
        this.totalAttackers = totalAttackers;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public String getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getTopCountry() {
        return topCountry;
    }

    public void setTopCountry(String topCountry) {
        this.topCountry = topCountry;
    }

    public String getTopIp() {
        return topIp;
    }

    public void setTopIp(String topIp) {
        this.topIp = topIp;
    }

    public int getHighRiskCount() {
        return highRiskCount;
    }

    public void setHighRiskCount(int highRiskCount) {
        this.highRiskCount = highRiskCount;
    }

    public long getTotalAttackers() {
        return totalAttackers;
    }

    public void setTotalAttackers(long totalAttackers) {
        this.totalAttackers = totalAttackers;
    }
}