package org.example.microservmaintenance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Maintenance {

    @Id
    @Column(name = "idMantenance")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "idScooter")
    private long idScooter;

    @Column(name ="fecha_inicio")
    private Date fecha_inicio;

    @Column(name = "estadoMantenimiento")
    private boolean finalizado;

}
