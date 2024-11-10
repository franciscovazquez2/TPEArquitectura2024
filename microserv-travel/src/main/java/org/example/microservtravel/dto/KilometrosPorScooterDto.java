package org.example.microservtravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KilometrosPorScooterDto {
    private Long scooterId;
    private long totalDistance;
    private long totalUsageTime;
    private boolean includePause;
}
