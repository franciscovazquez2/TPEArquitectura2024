package org.example.microservscooter.client;
import org.example.microservscooter.dto.parking.ParkingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "microserv-parking", url = "localhost:8080/api/parking")
public interface ParkingClient {

    @GetMapping("/{id}/estacionar")
    ParkingDto findParkingBuyId(@PathVariable Long id);

    @PutMapping("/{id}/ocupada")
    ParkingDto parkingChangeStatus(@PathVariable Long id);
}
