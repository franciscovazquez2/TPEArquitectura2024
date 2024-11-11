package org.example.microservtravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScooterReportXviajesDto {
    private Long idScooter;
    private int cantViajes;
    private long totalKilometers;
    private long totalUsageTime;
    private long totalPauseTime;
}
