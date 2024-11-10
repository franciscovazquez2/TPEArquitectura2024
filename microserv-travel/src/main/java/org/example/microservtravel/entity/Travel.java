package org.example.microservtravel.entity;

import lombok.AllArgsConstructor;
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
    private Long price;

    @Field(name = "kilometers")
    private Long kilometers;

    @Field(name = "usageTime")
    private long usageTime;

    @Field(name = "pauseTime")
    private long pauseTime;

    @Field(name = "has_pauses")
    private boolean hasPauses;

}
