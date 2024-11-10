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
public class Billing {

    @Id
    @Column(name = "id_factura")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "id_reserva", nullable = false)
    private Long idReserva;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;


    public Billing(LocalDate fechaEmision, Long idReserva, Long idUsuario, Double montoTotal) {
        this.fechaEmision = fechaEmision;
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.montoTotal = montoTotal;
    }
}