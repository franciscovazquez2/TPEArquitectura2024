package org.example.microservmaintenance.http.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.microservmaintenance.dto.scooter.ScooterDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceScooterResponse {

    private long id;
    private ScooterDTO scooter;
}
