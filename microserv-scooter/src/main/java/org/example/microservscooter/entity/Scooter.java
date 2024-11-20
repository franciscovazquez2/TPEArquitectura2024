package org.example.microservscooter.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_scooter;

    @Column(name = "parkingLat",nullable = false)
    private double latitude;

    @Column(name = "parkingLong",nullable = false)
    private double longitude;

    @Column(nullable = false)
    private Long kilometers;

    @Column(nullable = false)
    private int usageTime;

    @Column(nullable = false)
    private boolean start;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private boolean maintenance;

    @Column
    private Long idParking;

    public Scooter(Long id_scooter, double latitude, double longitude, Long kilometers, int usageTime, boolean start, boolean available, boolean maintenance) {
        this.id_scooter = id_scooter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.kilometers = kilometers;
        this.usageTime = usageTime;
        this.start = start;
        this.available = available;
        this.maintenance = maintenance;
        this.idParking = 0L;
    }


}
