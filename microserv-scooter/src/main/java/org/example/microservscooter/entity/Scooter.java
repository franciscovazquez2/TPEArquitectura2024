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

}
