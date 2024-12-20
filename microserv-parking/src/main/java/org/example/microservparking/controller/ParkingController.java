package org.example.microservparking.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservparking.dto.ParkingDto;
import org.example.microservparking.entity.Parking;
import org.example.microservparking.error.exception.ExistException;
import org.example.microservparking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/parking")
@Tag(name = "Parking", description = "Controller de estacionamiento")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    //Obtener el listado de paradas
    @Operation(
            summary = "Obtener estacionamientos",
            description = "Obtiene un listado de todos los estacionamientos",
            tags = {"Get","Parking"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                [
                                                      {
                                                        "id": 1,
                                                        "latitude": 37.7749,
                                                        "longitude": -122.4194,
                                                        "capacity": 3,
                                                        "actualCapacity": 2,
                                                        "available": true
                                                      },
                                                      {
                                                        "id": 2,
                                                        "latitude": 34.0522,
                                                        "longitude": -118.2437,
                                                        "capacity": 3,
                                                        "actualCapacity": 2,
                                                        "available": true
                                                      }
                                                ]
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllParkings(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.getAllParkings());
    }

    // Agregar parada
    @Operation(
            summary = "Crear un estacionamiento",
            description = "Crea un registro de estacionamiento",
            tags = {"Post","Parking"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del estacionamiento a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                    example = """
                                                {
                                                  "latitude": 0,
                                                  "longitude": 0,
                                                  "capacity": 10,
                                                  "actualCapacity": 10,
                                                  "available": true
                                                }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 10,
                                                  "latitude": 1,
                                                  "longitude": 1,
                                                  "capacity": 10,
                                                  "actualCapacity": 10,
                                                  "available": true
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear el estacionamiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "type": "about:blank",
                                                  "title": "Bad Request",
                                                  "status": 400,
                                                  "detail": "Failed to read request",
                                                  "instance": "/api/parking"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public @ResponseBody ResponseEntity<?> createParking(@RequestBody Parking newParking) throws ExistException {
            return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.createParking(newParking));

    }

    //eliminar parada
    @Operation(
            summary = "Borrar estacionamiento por id",
            description = "Borra un registro de estacionamiento mediante un id ingresado",
            tags = {"Delete","Parking","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 5,
                                                  "latitude": 0,
                                                  "longitude": 0,
                                                  "capacity": 10,
                                                  "actualCapacity": 10,
                                                  "available": true
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Error al eliminar el estacionamiento con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "La parada no existe. ID: 444",
                                                  "details": "/api/parking/444",
                                                  "status": "CONFLICT"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?>deleteParking(@PathVariable(value = "id")Long id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.deleteParking(id));
    }



    // Este endpoint es de comunicacion entre microservicio scooter y parking
    @Hidden //lo oculta de swagger porque es un endpoint privado
    @GetMapping("/{id}/estacionar")
    public @ResponseBody ParkingDto getParkingByScooter (@PathVariable(value="id")Long id) {
            return parkingService.getParking(id);
    }

    @Hidden //lo oculta de swagger porque es un endpoint privado
    @PutMapping("/{id}/ocupada")
    public @ResponseBody ParkingDto ocuparParking (@PathVariable(value="id") Long id){
        return parkingService.ocuparEstacionamiento(id);
    }


    //liberar un espacio de la parada (sacar un monopatin)
    @Operation(
            summary = "Desocupar espacio del estacionamiento",
            description = "Se libera un espacio del estacionamiento al retirar un monopatin",
            tags = {"Put","Parking","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 3,
                                                  "latitude": 40.7128,
                                                  "longitude": -74.006,
                                                  "capacity": 3,
                                                  "actualCapacity": 2,
                                                  "available": true
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "No se puede liberar el espacio del estacionamiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "no existe parking con ID: 3256",
                                                  "details": "/api/parking/3256/liberarEstacionamiento",
                                                  "status": "CONFLICT"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/liberarEstacionamiento")
    public @ResponseBody ResponseEntity<?>liberarEstacionamiento(@PathVariable(value="id")Long id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.liberarEstacionamiento(id));
    }

    @Operation(
            summary = "Obtener estacionamiento por id",
            description = "Obtiene un estacionamiento de parking mediante un id ingresado",
            tags = {"Get","Parking","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 3,
                                                  "latitude": 40.7128,
                                                  "longitude": -74.006,
                                                  "capacity": 3,
                                                  "actualCapacity": 2,
                                                  "available": true
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "No existe el parking con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "El parking no existe id: 11520",
                                                  "details": "/api/parking/11520",
                                                  "status": "CONFLICT"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingById(@PathVariable(name = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(parkingService.getParking(id));
    }

}