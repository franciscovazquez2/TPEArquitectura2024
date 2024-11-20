package org.example.microservscooter.controller;
import io.swagger.v3.oas.annotations.Hidden;
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
                                                  "usageTime": 0
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
                                                  "id_scooter": 13,
                                                  "latitude": 0,
                                                  "longitude": 0,
                                                  "kilometers": 0,
                                                  "usageTime": 0,
                                                  "available": false,
                                                  "maintenance": false,
                                                  "id_parking": null
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear el monopatin",
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
                                                  "instance": "/api/scooter"
                                                }
                                            """
                                    )
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
                            responseCode = "204",
                            description = "No se encontro el monopatin",
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                    {
                                                      "id_scooter": 12,
                                                      "latitude": 51.5074,
                                                      "longitude": -0.1278,
                                                      "kilometers": 2500,
                                                      "usageTime": 90,
                                                      "available": false,
                                                      "maintenance": true,
                                                      "id_parking": null
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "ID no encontrado",
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

    // ENDPOINT PARA EL MICROSERVICIO MAINTENANCE
    @Hidden //lo oculta de swagger porque es un endpoint privado
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                            
                                                    {
                                                      "id_scooter": 12,
                                                      "latitude": 51.5074,
                                                      "longitude": -0.1278,
                                                      "kilometers": 2500,
                                                      "usageTime": 90,
                                                      "available": false,
                                                      "maintenance": true,
                                                      "id_parking": null
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                            
                                                    {
                                                      "id_scooter": 12,
                                                      "latitude": 51.5074,
                                                      "longitude": -0.1278,
                                                      "kilometers": 2500,
                                                      "usageTime": 90,
                                                      "available": false,
                                                      "maintenance": true,
                                                      "id_parking": null
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                    {
                                                      "id_scooter": 12,
                                                      "latitude": 51.5074,
                                                      "longitude": -0.1278,
                                                      "kilometers": 2500,
                                                      "usageTime": 90,
                                                      "available": true,
                                                      "maintenance": false,
                                                      "id_parking": 1
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error al ubicar el monopatin, la parada no esta disponible",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                    {
                                                      "message": "No esta disponible la parada ID: 2",
                                                      "details": "/api/scooter/3/ubicar/2",
                                                      "status": "NOT_FOUND"
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "Error, ID de monopatin no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                    {
                                                      "message": "[409] during [GET] to [http://microserv-parking/api/parking/6555/estacionar] [ParkingClient#findParkingBuyId(Long)]: [{\\"message\\":\\"El parking no existe id: 6555\\",\\"details\\":\\"/api/parking/6555/estacionar\\",\\"status\\":\\"CONFLICT\\"}]",
                                                      "details": "/api/scooter/1/ubicar/6555",
                                                      "status": "NOT_FOUND"
                                                    }
                                            """
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}/ubicar/{id_parada}") //opcional (ubicar monopatin en parada)
    public @ResponseBody ResponseEntity<?> ubicarScooterEnParada(@PathVariable(value="id")Long id,
                                                                 @PathVariable(value = "id_parada")Long id_parada){
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.ubicarScooterEnParada(id,id_parada));
    }

    //devuelve cantidad scooter en mantenimiento vs operacion (servicio E)
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                            
                                                    {
                                                      "cantScooterActive": 9,
                                                      "cantScooterInactive": 7
                                                    }
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/inMaintenance-Operate")
    public @ResponseBody ResponseEntity<?>getScooterByOperation(){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooterByOperation());
    }

    //devuelve los scooters cercanos a una coordenada    (servicio G)

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
                                                      "id_parking": 1
                                                    },
                                                    {
                                                      "id_scooter": 2,
                                                      "latitude": 34.0522,
                                                      "longitude": -118.2437,
                                                      "kilometers": 2000,
                                                      "usageTime": 180,
                                                      "available": true,
                                                      "maintenance": false,
                                                      "id_parking": 2
                                                    }
                                                ]
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/nearlyScooters/{latitude}/{longitude}/{distance}")
    public @ResponseBody ResponseEntity<?>getNearlyScooters(@PathVariable(value="latitude") double latitude,@PathVariable(value="longitude") double longitude,@PathVariable (value="distance")double distance){
        return ResponseEntity.status(HttpStatus.OK).body(scooterService.getNearlyScooters(latitude,longitude,distance));
    }

}
