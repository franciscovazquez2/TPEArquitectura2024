package org.example.microservtravel.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document()
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Travel {

    @Id
    private String id_viaje;

    @Field(name = "User")
    private Long id_user;

    @Field(name = "Scooter")
    private Long id_scooter;

    @Field(name = "date")
    private Date date;

    @Field(name = "price")
    private Double price;

    @Field(name = "kilometers")
    private Long kilometers;

    @Field(name = "usageTime")
    private long usageTime;

    @Field(name = "pauseTime")
    private long pauseTime;

    @Field(name = "has_pauses")
    private boolean hasPauses;


    public Travel(Long id_user, Long id_scooter, Date date, Double price, Long kilometers, long usageTime, long pauseTime, boolean hasPauses) {
        this.id_user = id_user;
        this.id_scooter = id_scooter;
        this.date = date;
        this.price = price;
        this.kilometers = kilometers;
        this.usageTime = usageTime;
        this.pauseTime = pauseTime;
        this.hasPauses = hasPauses;
    }
}
