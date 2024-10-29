package org.example.microservmaintenance.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Maintenance {

    @Id
    @Column(name = "idMantenance")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idScooter")
    private long idScooter;

    public Maintenance() {}

    public Maintenance(long id , long idScooter){
        this.id=id;
        this.idScooter=idScooter;
    }

}
