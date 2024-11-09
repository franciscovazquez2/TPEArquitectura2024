package org.example.microservparking.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Parking {

    @Id
    @Column(name = "idParking")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "parkingLatitude")
    private double latitude;

    @Column(name = "parkingLongitude")
    private double longitude;

    @Column(name = "parkinCapacity")
    private int capacity;

    @Column(name="actualCapacity")
    private int actualCapacity;

    @Column(name = "parkingAviable")
    private boolean available;


    public Parking() {}

    public Parking(long id,double latitude, double longitude, int capacity, boolean available) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacity = capacity;
        this.available = available;
        this.actualCapacity=capacity;
    }

    public void decreaseCapacity() {
        if (this.capacity > 0) {
            this.capacity--;
        }
    }

    public void incrementCapacity(){
        if(this.actualCapacity<this.capacity){
            this.actualCapacity++;
        }
    }

    public boolean isAvailable(){
        return this.capacity>0;
    }

}