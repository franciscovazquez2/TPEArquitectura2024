package org.example.microservtravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelScooterReportDto {

    private Long idScooter;
    private long kilometers;
    private long usageTime;
    private boolean includePause;

}
