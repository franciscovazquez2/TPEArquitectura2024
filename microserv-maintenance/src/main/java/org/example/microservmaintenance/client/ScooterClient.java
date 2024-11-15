package org.example.microservmaintenance.client;

import org.example.microservmaintenance.dto.scooter.ScooterDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microserv-scooter")
public interface ScooterClient {

    @GetMapping(value = "api/scooter/search-maintenance/{id}")
    ScooterDTO findScooterBuyId(@PathVariable Long id);
}
