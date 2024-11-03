package org.example.microservscooter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_scooter;

    @Column(name = "parkingLat")
    private double latitude;

    @Column(name = "parkingLong")
    private double longitude;

    @Column
    private Long kilometers;

    @Column
    private int usageTime;

    @Column
    private boolean start;

    @Column
    private boolean available;

    @Column
    private boolean maintenence;

    public Scooter() {}

    public Scooter(double latitude, double longitude, Long kilometers, int usageTime, boolean start, boolean available, boolean maintenence) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.kilometers = kilometers;
        this.usageTime = usageTime;
        this.start = start;
        this.available = available;
        this.maintenence = maintenence;
    }
}
