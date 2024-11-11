package org.example.microservscooter.dto.parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParkingDto {
        private Long id;
        private double latitude;
        private double longitude;
        private int capacity;
        private int actualCapacity;
        private boolean available;
}
