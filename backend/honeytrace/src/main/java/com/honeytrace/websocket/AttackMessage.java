package com.honeytrace.websocket;

public class AttackMessage {

    private String ipAddress;
    private String country;
    private String city;
    private Integer riskScore;

    public AttackMessage() {
    }

    public AttackMessage(String ipAddress,
                         String country,
                         String city,
                         Integer riskScore) {

        this.ipAddress = ipAddress;
        this.country = country;
        this.city = city;
        this.riskScore = riskScore;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Integer riskScore) {
        this.riskScore = riskScore;
    }
}