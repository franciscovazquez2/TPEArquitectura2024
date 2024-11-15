package org.example.microservbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillingDTO {
    private Long id;
    private LocalDate fechaEmision;
    private Long idReserva;
    private Long idUsuario;
    private Double montoTotal;
}
