package org.example.microservscooter.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "microserv-parking", url = "localhost:8080/api/parking")
public interface ParkingClient {


    // ARREGLAR ESTOOO !!!
    @PutMapping("/{id}/estacionar")
    void findScooterBuyId(@PathVariable Long id);
}
