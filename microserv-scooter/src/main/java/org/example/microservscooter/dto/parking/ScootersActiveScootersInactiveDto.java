package org.example.microservscooter.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScootersActiveScootersInactiveDto {

    private int cantScooterActive;
    private int cantScooterInactive;

}
