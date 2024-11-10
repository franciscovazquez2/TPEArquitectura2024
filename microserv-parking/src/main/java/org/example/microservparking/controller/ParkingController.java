package org.example.microservparking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.BadRequestException;
import org.example.microservparking.dto.ParkingDto;
import org.example.microservparking.entity.Parking;
import org.example.microservparking.error.exception.ExistException;
import org.example.microservparking.error.exception.NotExistsException;
import org.example.microservparking.error.exception.ParkingFullExection;
import org.example.microservparking.error.exception.RequestBadException;
import org.example.microservparking.services.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al listar las paradas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllParkings(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.getAllParkings());
        } catch (BadRequestException e) {
        throw new RequestBadException("No se encontraron Paradas");
        }
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
                            schema = @Schema(implementation = Parking.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear el estacionamiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PostMapping
    public @ResponseBody ResponseEntity<?> createParking(@RequestBody Parking newParking) throws ExistException {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(parkingService.createParking(newParking));
        } catch (BadRequestException e) {
            throw new RequestBadException("Error al crear la parada " + newParking.toString());
        }

    }

    //eliminar parada
    @Operation(
            summary = "Borrar estacionamiento por id",
            description = "Borra un registro de estacionamiento mediante un id ingresado",
            tags = {"Delete","Parking","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "El estacionamiento fue eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al eliminar el estacionamiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?>deleteParking(@PathVariable(value = "id")Long id)throws NotExistsException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.deleteParking(id));
        }catch (BadRequestException e){
            throw new RequestBadException("Fallo al eliminar el ID: " + id);
        }
    }

    //ocupar un espacio de la parada (estacionar o dejar un monopatin)
    @Operation(
            summary = "Ocupar espacio de estacionamiento",
            description = "Ocupa un espacio en el estacionamiento al dejar el monopatin",
            tags = {"Put","Parking","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se puede ocupar el espacio del estacionamiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/{id}/estacionar")
    public @ResponseBody ResponseEntity<?>ocuparEstacionamiento(@PathVariable(value="id")Long id)throws ParkingFullExection, NotExistsException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.ocuparEstacionamiento(id));
        }catch (BadRequestException e){
            throw new RequestBadException("No se pudo ocupar el estacionamiento");
        }
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
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se puede liberar el espacio del estacionamiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/{id}/liberarEstacionamiento")
    public @ResponseBody ResponseEntity<?>liberarEstacionamiento(@PathVariable(value="id")Long id)throws NotExistsException{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(parkingService.liberarEstacionamiento(id));
        }catch (BadRequestException e){
            throw new RequestBadException("error al liberar estacionamiento");
        }
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
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No existe el parking con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Fallo al buscar el parking con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingById(@PathVariable(name = "id") Long id){
        try{
            ParkingDto parking = parkingService.getParking(id);
            if(parking==null){
                throw new NotExistsException("No existe el parking con ID: " + id);
            }
            return ResponseEntity.status(HttpStatus.OK).body(parking);
        }catch (BadRequestException e){
            throw new RequestBadException("Fallo al buscar el parking con id: " +id);
        }
    }

}