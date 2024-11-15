package org.example.microservmaintenance.dto.scooter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ScooterDTO {
    private Long id_scooter;
    private double latitude;
    private double longitude;
    private Long kilometers;
    private int usageTime;
    private boolean maintenence;

    public boolean isEmpty() {
        return (this.id_scooter == null);}
}
