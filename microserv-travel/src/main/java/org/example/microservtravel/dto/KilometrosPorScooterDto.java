package org.example.microservtravel.dto;

public class KilometrosPorScooterDto {

    private Long scooterId;
    private long totalDistance;
    private long totalUsageTime;
    private boolean includePause;

    public KilometrosPorScooterDto(Long scooterId, long totalDistance, long totalUsageTime, boolean includePause) {
        this.scooterId = scooterId;
        this.totalDistance = totalDistance;
        this.totalUsageTime=totalUsageTime;
        this.includePause=includePause;
    }

    public Long getScooterId() {
        return scooterId;
    }

    public long getTotalDistance() {
        return totalDistance;
    }

    public long getTotalUsageTime() {
        return totalUsageTime;
    }

    public boolean isHasPaused() {
        return includePause;
    }
}
