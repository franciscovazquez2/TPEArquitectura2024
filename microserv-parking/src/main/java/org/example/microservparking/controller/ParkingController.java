package org.example.microservparking.controller;

import org.example.microservparking.services.ParkingService;

@RestController
@RequestMapping("api/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    //Obtener el listado de paradas
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllParkings(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.getAllParkins());
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

}