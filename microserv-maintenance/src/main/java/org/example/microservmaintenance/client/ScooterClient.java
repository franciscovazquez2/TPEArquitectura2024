package org.example.microservmaintenance.client;

import org.example.microservmaintenance.dto.scooter.ScooterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microserv-scooter", url = "localhost:8080/api/scooter")
public interface ScooterClient {

    @GetMapping("/search-maintenance/{id}")
    ScooterDTO findScooterBuyId(@PathVariable Long id);
}
