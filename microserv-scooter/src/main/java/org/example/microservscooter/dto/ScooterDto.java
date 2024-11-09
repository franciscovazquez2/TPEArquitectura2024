package org.example.microservscooter.dto;

import jakarta.persistence.Column;

public class ScooterDto {
    private Long id_scooter;

    private double latitude;

    private double longitude;

    private Long kilometers;

    private int usageTime;

    private boolean start;

    private boolean available;

    private boolean maintenence;

    public ScooterDto(Long id_scooter, double latitude, double longitude, Long kilometers, int usageTime, boolean start, boolean available, boolean maintenence) {
        this.id_scooter = id_scooter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.kilometers = kilometers;
        this.usageTime = usageTime;
        this.start = start;
        this.available = available;
        this.maintenence = maintenence;
    }

    public Long getId_scooter() {
        return id_scooter;
    }

    public void setId_scooter(Long id_scooter) {
        this.id_scooter = id_scooter;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Long getKilometers() {
        return kilometers;
    }

    public void setKilometers(Long kilometers) {
        this.kilometers = kilometers;
    }

    public int getUsageTime() {
        return usageTime;
    }

    public void setUsageTime(int usageTime) {
        this.usageTime = usageTime;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isMaintenence() {
        return maintenence;
    }

    public void setMaintenence(boolean maintenence) {
        this.maintenence = maintenence;
    }
}
