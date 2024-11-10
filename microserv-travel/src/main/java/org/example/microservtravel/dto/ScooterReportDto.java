package org.example.microservtravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScooterReportDto {

    private Long idScooter;
    private Long totalKilometers;
    private Long totalUsageTime;
    private Long totalPauseTime;
}
