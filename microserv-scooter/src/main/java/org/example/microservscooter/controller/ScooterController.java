package org.example.microservscooter.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.error.exception.NotExistsException;
import org.example.microservscooter.service.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("api/scooter")
@Tag(name = "Scooter", description = "Controller de monopatin")
public class ScooterController {

    @Autowired
    private ScooterService scooterService;


    @GetMapping
    @Operation(
            summary = "Obtener monopatines",
            description = "Obtiene un listado de todos los monopatines",
            tags = {"Get","Scooter"},
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
                                                      "id_scooter": 1,
                                                      "latitude": 37.7749,
                                                      "longitude": -122.4194,
                                                      "kilometers": 1500,
                                                      "usageTime": 120,
                                                      "available": true,
                                                      "maintenance": false,
                                                      "id_parking": null
                                                    },
                                                    {
                                                      "id_scooter": 2,
                                                      "latitude": 34.0522,
                                                      "longitude": -118.2437,
                                                      "kilometers": 2000,
                                                      "usageTime": 180,
                                                      "available": true,
                                                      "maintenance": false,
                                                      "id_parking": null
                                                    }
                                                ]
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al listar los monopatines",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    public @ResponseBody ResponseEntity<?> getAllScooter() {
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getAllScooter());
    }


    @Operation(
            summary = "Crear monopatin",
            description = "Crea un registro de monopatin",
            tags = {"Post","Scooter"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del monopatin a crear",
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
                                                  "kilometers": 0,
                                                  "usageTime": 0,
                                                  "start": true,
                                                  "available": true,
                                                  "maintenance": false,
                                                  "idParking": 0
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
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear el monopatin",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PostMapping()
    public @ResponseBody ResponseEntity<?> createScooter(@RequestBody Scooter newScooter) {
        return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.createScooter(newScooter));
    }


    @Operation(
            summary = "Obtener monopatin por id",
            description = "Obtiene un registro de monopatin mediante un id ingresado",
            tags = {"Get","Scooter","Id"},
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
                                                      "id_scooter": 1,
                                                      "latitude": 37.7749,
                                                      "longitude": -122.4194,
                                                      "kilometers": 1500,
                                                      "usageTime": 120,
                                                      "available": true,
                                                      "maintenance": false,
                                                      "id_parking": null
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al buscar monopatin",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getScooter(@PathVariable(value = "id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooter(id));
    }


    @Operation(
            summary = "Borrar monopatin por id",
            description = "Borra un registro de monopatin mediante un id ingresado",
            tags = {"Delete","Scooter","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "El scooter fue eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "ID no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al eliminar el scooter",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> deleteByID(@PathVariable(value = "id") Long id){
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.delete(id));
    }

    // ENDPOINT PARA QUE EL MICROSERVICIO MAINTENANCE
    /* NO DEBERIA ESTAR VISIBLE ¿BORRAR SWAGGER?
    @Operation(
            summary = "Buscar monopatin desde mentenimiento",
            description = "Busca un monopatin desde mantenimiento ",
            tags = {"Get","Scooter","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ScooterDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Id inexistente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al solicitar el id",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )*/
    @GetMapping("/search-maintenance/{id}")
    public @ResponseBody Optional<ScooterDTO> getScooterMaintenance(@PathVariable(value = "id") Long id) {
        Optional<ScooterDTO> scooterDTO = scooterService.getScooterByMaintenance(id);
        if(!scooterDTO.isPresent()){
            throw  new NotExistsException("Id inexistente" + id);
        }
        return scooterDTO;
    }


    //	Registrar monopatín en mantenimiento (debe marcarse como no disponible para su uso)
    @Operation(
            summary = "Comenzar mantenimiento",
            description = "Se modifica el registro de un monopatin marcando el inicio de un mantenimiento",
            tags = {"Put","Scooter","Id"},
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
                            description = "No se puede iniciar el mantenimiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/inicio-mantenimiento/{id}")
    public @ResponseBody ResponseEntity<?>startMaintenance(@PathVariable(value="id")Long id){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.startMaintenance(id));
    }

    //fin de mantenimiento de monopatin
    @Operation(
            summary = "Finalizar mantenimiento",
            description = "Se modifica el registro de un monopatin marcando el fin de un mantenimiento",
            tags = {"Put","Scooter","Id"},
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
                            description = "Error al finalizar el mantenimiento con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/fin-mantenimiento/{id}")
    public @ResponseBody ResponseEntity<?>finishMaintenance(@PathVariable(value="id")Long id){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.finishMaintenance(id));
    }

    //registrar monopatin a una parda
    @Operation(
            summary = "Ubicar monopatin en estacionamiento",
            description = "Se ubica el monopatin en un estacionamiento mediante el id del mismo y el id del estacionamiento",
            tags = {"Put","Scooter","Id"},
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
                            description = "Error al ubicar el monopatin con los ID ingresados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/{id}/ubicar/{id_parada}")
    public @ResponseBody ResponseEntity<?> ubicarScooterEnParada(@PathVariable(value="id")Long id,
                                                                 @PathVariable(value = "id_parada")Long id_parada){
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.ubicarScooterEnParada(id,id_parada));
    }

    //devuelve cantidad scooter en mantenimiento vs operacion
    @Operation(
            summary = "Obtener monopatines en mantenimiento y en operacion",
            description = "Obtiene la cantidad de monopatines que estan en mantenimiento y la cantidad de monopatines que estan en operacion",
            tags = {"Get","Scooter"},
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
                            description = "Error al listar los monopatines",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping("/inMaintenance-Operate")
    public @ResponseBody ResponseEntity<?>getScooterByOperation(){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooterByOperation());
    }


    @Operation(
            summary = "Obtener monopatines cercanos",
            description = "Obtiene un listado de todos los monopatines cercanos a una ubicacion",
            tags = {"Get","Scooter"},
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
                            description = "Error al listar los monopatines",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping("/nearlyScooters/{latitude}/{longitude}/{distance}")
    public @ResponseBody ResponseEntity<?>getNearlyScooters(@PathVariable(value="latitude") double latitude,@PathVariable(value="longitude") double longitude,@PathVariable (value="distance")double distance){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getNearlyScooters(latitude,longitude,distance));
    }

}
