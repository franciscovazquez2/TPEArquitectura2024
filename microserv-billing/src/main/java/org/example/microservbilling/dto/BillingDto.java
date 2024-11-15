package org.example.microservbilling.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingDto {

    private long id;
    private LocalDate fechaEmision;
    private Long idReserva;
    private Long idUsuario;
    private Double montoTotal;
}
