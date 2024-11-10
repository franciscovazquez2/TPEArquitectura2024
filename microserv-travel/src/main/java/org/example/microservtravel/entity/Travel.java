package org.example.microservtravel.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document()
@Data
public class Travel {

    @Id
    private String id_viaje;

    @Field(name = "User")
    private Long id_user;

    @Field(name = "Scooter")
    private Long id_scooter;

    @Field()
    private Date date;

    @Field()
    private Long price;

    @Field(name = "kilometers")
    private Long kilometers;

    @Field(name = "usageTime")
    private long usageTime;

    @Field(name = "pauseTime")
    private long pauseTime;

    @Field(name = "has_pauses")
    private boolean hasPauses;

    public Travel() {
    }

    public Travel(String id_viaje, Long id_user, Long id_scooter, Date date, Long price,Long kilometers,long usageTime, long pauseTime,boolean hasPauses) {
        this.id_viaje = id_viaje;
        this.id_user = id_user;
        this.id_scooter = id_scooter;
        this.date = date;
        this.price = price;
        this.kilometers=kilometers;
        this.usageTime=usageTime;
        this.pauseTime=pauseTime;
        this.hasPauses=hasPauses;
    }
}
