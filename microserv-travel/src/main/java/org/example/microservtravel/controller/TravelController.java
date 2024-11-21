package org.example.microservtravel.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservtravel.dto.TravelDto;
import org.example.microservtravel.error.dto.MessageDTO;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.error.exception.RequestBadException;
import org.example.microservtravel.repository.TravelRepository;
import org.example.microservtravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/travel")
@Tag(name = "Travel", description = "Controller de viaje")
public class TravelController {

    @Autowired
    private TravelService travelService;
    @Autowired
    private TravelRepository travelRepository;


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
        return ResponseEntity.status(HttpStatus.OK).body(travelService.getAllTravels());
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
        return ResponseEntity.status(HttpStatus.CREATED).body(travelService.createTravel(newTravel));
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
    public @ResponseBody ResponseEntity<?> getTravel(@PathVariable(value = "id") String id){
        return ResponseEntity.status(HttpStatus.OK).body(travelService.getTravel(id));
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
    public @ResponseBody ResponseEntity<?> deleteByID(@PathVariable(value = "id") String id) throws RequestBadException {
        try{
            travelService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("El viaje fue eliminado correctamente","id eliminado: "+id,HttpStatus.OK));
        }catch(Exception e1){
            throw new RequestBadException("No se pudo eliminar el id: " +id);
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
    public @ResponseBody ResponseEntity<?> reporteScooterPorKilometros ( @RequestParam ( defaultValue="false" ) boolean includePause ){
        return ResponseEntity.status(HttpStatus.OK).body(travelService.reporteScooterPorKilometros(includePause));
    }


    @GetMapping("/reporte/anio/{anio}/cantViaje/{cantViaje}")
    public @ResponseBody ResponseEntity<?> reporteScooterConMasDeXkilometros ( @RequestParam(value="anio") int anio, @RequestParam (value="cantViaje") int cantViaje){
        return ResponseEntity.status(HttpStatus.OK).body(travelService.reporteScooterConMasDeXkilometros(anio,cantViaje));

    }

    @GetMapping(value="api/travel/startTravel/{id_user}/{id_scooter}")
    TravelDto startTravel (@PathVariable (value = "id_user") Long id_user, @PathVariable (value="id_scooter") Long id_scooter){
        Travel travel=travelRepository.save(new Travel(id_user,id_scooter,new Date(), 0.0, 0L,0L,0L,false));
        return TravelDto.builder()
                .id_viaje(travel.getId_viaje())
                .id_user(travel.getId_user())
                .id_scooter(travel.getId_scooter())
                .date(travel.getDate())
                .price(travel.getPrice())
                .kilometers(travel.getKilometers())
                .usageTime(travel.getUsageTime())
                .pauseTime(travel.getPauseTime())
                .hasPauses(travel.isHasPauses()).build();
    }

}
