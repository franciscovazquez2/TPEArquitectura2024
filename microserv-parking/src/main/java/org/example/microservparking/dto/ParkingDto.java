package org.example.microservparking.dto;

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

    //necesario para csv
    public ParkingDto(double latitude, double longitude, int capacity, int actualCapacity, boolean available) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.actualCapacity = actualCapacity;
        this.available = available;
    }
}
