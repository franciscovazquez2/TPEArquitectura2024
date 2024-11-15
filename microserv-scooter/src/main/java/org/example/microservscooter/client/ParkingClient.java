package org.example.microservscooter.client;
import org.example.microservscooter.dto.parking.ParkingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "microserv-parking")
public interface ParkingClient {

    @GetMapping("api/parking/{id}/estacionar")
    ParkingDto findParkingBuyId(@PathVariable Long id);

    @PutMapping("api/parking/{id}/ocupada")
    ParkingDto parkingChangeStatus(@PathVariable Long id);
}
