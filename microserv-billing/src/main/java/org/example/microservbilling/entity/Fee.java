package org.example.microservbilling.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Fee {

    @Id
    @Column(name = "id_tarifa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "monto", nullable = false)
    private Double monto;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Column(name = "tipo", nullable = false)
    private String tipo;

    public Fee() {}

    public Fee(Double monto, LocalDate fechaInicio, LocalDate fechaFin, String tipo) {
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipo = tipo;
    }

    public Fee(Double monto, LocalDate fechaInicio, String tipo) {
        this.monto = monto;
        this.fechaInicio = fechaInicio;
        this.tipo = tipo;
    }
}
