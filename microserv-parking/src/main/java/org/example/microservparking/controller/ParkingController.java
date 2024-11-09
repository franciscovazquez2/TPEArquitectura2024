package org.example.microservparking.controller;

import org.example.microservparking.entity.Parking;
import org.example.microservparking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    //Obtener el listado de paradas
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllParkings(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.getAllParkings());
        } catch (Exception e) {
            String errorJson = "{\"message\":\"Error al listar las paradas\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    // Agregar parada
    @PostMapping
    public @ResponseBody ResponseEntity<?> createParking(@RequestBody Parking newParking){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.createParking(newParking));
        } catch (Exception e) {
            String errorJson = "{\"message\":\"Error al crear la parada\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }

    }
    //eliminar parada
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?>deleteParking(@PathVariable(value = "id")Long id){
        try{
            parkingService.deleteParking(id);
            return ResponseEntity.status(HttpStatus.OK).body("El scooter fue eliminado correctamente");
        }catch (Exception e){
            String errorJson = "{\"message\":\"Error al eliminar la parada\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    //ocupar un espacio de la parada (estacionar o dejar un monopatin)
    @PutMapping("/{id}/estacionar")
    public @ResponseBody ResponseEntity<?>ocuparEstacionamiento(@PathVariable(value="id")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.ocuparEstacionamiento(id));
        }catch (Exception e){
            String errorJson = "{\"message\":\"Error al intentar estacionar\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }



    //liberar un espacio de la parada (sacar un monopatin)
    @PutMapping("/{id}/liberarEstacionamiento")
    public @ResponseBody ResponseEntity<?>liberarEstacionamiento(@PathVariable(value="id")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.liberarEstacionamiento(id));
        }catch (Exception e){
            String errorJson = "{\"message\":\"Error al intentar liberar el estacionamiento\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }
}