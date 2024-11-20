package org.example.microservmaintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.microservmaintenance.error.dto.MessageDTO;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceDTO {
    private Long id;
    private Long id_scooter;
    private LocalDate fecha_mantenimiento;
    private boolean finalizado;

    public boolean isEmpty(){
        return this.id == null;
    }
}
