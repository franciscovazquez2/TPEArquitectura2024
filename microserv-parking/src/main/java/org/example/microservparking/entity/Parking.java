package org.example.microservparking.entity;

import jakarta.persistance.*;
import lombok.Data;

@Entity
@Data
public class Parking {

    @id
    @Column(name = "idParking")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "parkingLatitude")
    private double latitude;

    @Column(name = "parkingLongitude")
    private double longitude;

    @Column(name = "parkinCapacity")
    private int capacity;

    @Column(name = "parkingAviable")
    private boolean available;

    public Parking() {}

    public Parking(long id,double latitude, double longitude, int capacity, boolean available) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.available = available;
    }

}