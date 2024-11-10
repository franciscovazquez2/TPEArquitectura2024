package org.example.microservbilling.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fee {

    @Id
    @Column(name = "id_tarifa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;


    @Column(name = "tipo", nullable = false)
    private String tipo;



    public Fee(Double monto, LocalDate fechaInicio, String tipo) {
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.tipo = tipo;
    }
}
