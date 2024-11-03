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


    public Travel() {
    }

    public Travel(String id_viaje, Long id_user, Long id_scooter, Date date, Long price) {
        this.id_viaje = id_viaje;
        this.id_user = id_user;
        this.id_scooter = id_scooter;
        this.date = date;
        this.price = price;
    }
}
