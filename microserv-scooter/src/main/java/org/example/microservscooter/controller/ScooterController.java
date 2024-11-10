package org.example.microservscooter.controller;

import jakarta.ws.rs.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.error.dto.MessageDTO;
import org.example.microservscooter.error.exception.NotExistsException;
import org.example.microservscooter.error.exception.NotFoundException;
import org.example.microservscooter.error.exception.RequestBadException;
import org.example.microservscooter.service.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public @ResponseBody ResponseEntity<?> getAllScooter() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(scooterService.getAllScooter());
        } catch (Exception e) {
        throw new RequestBadException("Fallo al listar todos los scooter");
        }
    }


    @PostMapping()
    @Operation(
            summary = "Crear monopatin",
            description = "Crea un registro de monopatin",
            tags = {"Post","Scooter"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del monopatin a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Scooter.class)
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
    public @ResponseBody ResponseEntity<?> createScooter(@RequestBody Scooter newScooter) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.createScooter(newScooter));
        }catch (Exception e){
        throw new RequestBadException("Error al crear un Scooter");
        }
    }

    @GetMapping("/{id}")
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
                                    schema = @Schema(implementation = ResponseEntity.class)
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
    public @ResponseBody ResponseEntity<?> getScooter(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooter(id));
        }catch (Exception e){
            throw new RequestBadException("Error al buscar el scooter " + id);
            }
    }

    @DeleteMapping("/{id}")
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
                            description = "Id no encontrado",
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
    public @ResponseBody ResponseEntity<?> deleteByID(@PathVariable(value = "id") Long id){
        try{
            scooterService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("El scooter fue eliminado correctamente","id eliminado: "+id,HttpStatus.OK));
        }catch(EmptyResultDataAccessException e1){
            throw new NotFoundException("ID no encontrado: " + id);
        } catch (Exception e) {
            throw new RequestBadException("Error al eliminar el id " + id);
        }
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
                            responseCode = "400",
                            description = "Id inexistente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )

                    ),
                    @ApiResponse(
                            responseCode = "404",
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
        try{
            Optional<ScooterDTO> scooterDTO = scooterService.getScooterByMaintenance(id);
            if(!scooterDTO.isPresent()){
                throw  new NotExistsException("Id inexistente" + id);
            }
            return scooterDTO;
        } catch (Exception e) {
            throw new RequestBadException("error al querer solicitar el id " + id);
        }
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
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.startMaintenance(id));
        }catch(BadRequestException e){
            throw new RequestBadException("Fallo en el inicio de mantenimiento");
        }
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
                            description = "No se puede finalizar el mantenimiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/fin-mantenimiento/{id}")
    public @ResponseBody ResponseEntity<?>finishMaintenance(@PathVariable(value="id")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.finishMaintenance(id));
        }catch(Exception e){
            throw new RequestBadException("Fallo al finalizar mantenimiento del scooter id: " +id);
        }
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
                            description = "No se puede ubicar el monopatin",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/{id}/ubicar/{id_parada}")
    public @ResponseBody ResponseEntity<?> ubicarScooterEnParada(@PathVariable(value="id")Long id,@PathVariable(value = "id_parada")Long id_parada){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.ubicarScooterEnParada(id,id_parada));
        }catch(BadRequestException e){
            throw new RequestBadException("Fallo al ubicar el Scooter id :" +id + " en parada " + id_parada);
        }
    }

}
