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
