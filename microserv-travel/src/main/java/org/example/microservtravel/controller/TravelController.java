package org.example.microservtravel.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservtravel.error.dto.MessageDTO;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/travel/")
@Tag(name = "Travel", description = "Controller de viaje")
public class TravelController {

    @Autowired
    private TravelService travelService;


@Operation(
            summary = "Obtener viajes",
            description = "Obtiene un listado de todos los viajes",
            tags = {"Get","Travel"},
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
                            description = "Error al listar los viajes",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllTravels() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(travelService.getAllTravels());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageDTO("Error al listar los viajes","",HttpStatus.BAD_REQUEST));
        }
    }

    @Operation(
            summary = "Crear viaje",
            description = "Crea un registro de viaje",
            tags = {"Post","Scooter"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del viaje a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Travel.class)
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
                            description = "Error al crear el viaje",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PostMapping()
    public @ResponseBody ResponseEntity<?> createTravel(@RequestBody Travel newTravel) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(travelService.createTravel(newTravel));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageDTO("Error al crear el viaje","",HttpStatus.BAD_REQUEST));
        }
    }

@Operation(
            summary = "Obtener viaje por id",
            description = "Obtiene un registro de viaje mediante un id ingresado",
            tags = {"Get","Travel","Id"},
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
                            description = "Error al buscar viaje",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getTravel(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(travelService.getTravel(id));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageDTO("Error al buscar el viaje","ID: " + id,HttpStatus.BAD_REQUEST));
        }
    }


    @Operation(
            summary = "Borrar viaje por id",
            description = "Borra un registro de viaje mediante un id ingresado",
            tags = {"Delete","Travel","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "El viaje fue eliminado correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "El id ingresado no existe",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "La consulta no es correcta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> deleteByID(@PathVariable(value = "id") Long id){
        try{
            travelService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("El viaje fue eliminado correctamente","id eliminado: "+id,HttpStatus.OK));
        }catch(EmptyResultDataAccessException e1){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageDTO("El id no existe","ID: " + id,HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageDTO("La consulta no es correcta","ID: " + id,HttpStatus.BAD_REQUEST));
        }
    }

    @Operation(
            summary = "Obtener monopatines por kilometros",
            description = "Obtiene un listado de uso de los monopatines por kilometros con o sin pausas dependiendo lo ingresado " +
                    "por defecto devuelve sin pausas",
            tags = {"Get","Travel"},
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
    @GetMapping("/reporte/scooter/{includePause}")
    public @ResponseBody ResponseEntity<?>reporteScooterPorKilometros(@RequestParam(defaultValue="false")boolean includePause){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(travelService.reporteScooterPorKilometros(includePause));
        }catch (Exception e ){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("No se puede acceder al reporte");
        }
    }

}
