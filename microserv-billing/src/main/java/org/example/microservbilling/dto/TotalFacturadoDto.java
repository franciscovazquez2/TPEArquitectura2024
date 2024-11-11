package org.example.microservbilling.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalFacturadoDto {

    private Double totalFacturado;
    private int year;
    private int startMonth;
    private int endMonth;

}
