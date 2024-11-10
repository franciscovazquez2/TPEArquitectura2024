package org.example.microservtravel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelDto {

        private String id_viaje;

        private Long id_user;

        private Long id_scooter;

        private Date date;

        private Long price;

        private Long kilometers;

        private long usageTime;

        private long pauseTime;

        private boolean hasPauses;

}
