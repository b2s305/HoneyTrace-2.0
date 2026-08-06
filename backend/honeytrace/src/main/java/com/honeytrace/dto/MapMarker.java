package com.honeytrace.dto;

public class MapMarker {

    private String ip;
    private String country;
    private String city;
    private Double latitude;
    private Double longitude;

    public MapMarker() {
    }

    public MapMarker(String ip,
                     String country,
                     String city,
                     Double latitude,
                     Double longitude) {

        this.ip = ip;
        this.country = country;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

}