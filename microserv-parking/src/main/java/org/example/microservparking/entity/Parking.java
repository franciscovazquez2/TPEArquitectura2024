package org.example.microservparking.entity;
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
public class Parking {

    @Id
    @Column(name = "idParking")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parkingLatitude")
    private double latitude;

    @Column(name = "parkingLongitude")
    private double longitude;

    @Column(name = "parkinCapacity")
    private int capacity;

    @Column(name = "actualCapacity")
    private int actualCapacity;

    @Column(name = "parkingAviable")
    private boolean available;

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