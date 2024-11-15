package org.example.microservmaintenance.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.microservmaintenance.error.dto.MessageDTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MaintenanceDTO {
    private Long id;
    private Long id_scooter;
    private MessageDTO message;

    public boolean isEmpty(){
        return this.id == null;
    }
}
