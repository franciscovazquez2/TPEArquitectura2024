package org.example.microservscooter.controller;

import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.error.dto.MessageDTO;
import org.example.microservscooter.error.exception.NotExistsException;
import org.example.microservscooter.error.exception.NotFoundIDException;
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
public class ScooterController {

    @Autowired
    private ScooterService scooterService;


    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllScooter() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(scooterService.getAllScooter());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageDTO("Error al listar todos los scooter","",HttpStatus.BAD_REQUEST));
        }
    }


    @PostMapping()
    public @ResponseBody ResponseEntity<?> createScooter(@RequestBody Scooter newScooter) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.createScooter(newScooter));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new MessageDTO("Error al crear el Scooter",newScooter.toString(),HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getScooter(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooter(id));
        }catch (Exception e){
            throw new RequestBadException("Error al buscar el scooter " + id);
            }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> deleteByID(@PathVariable(value = "id") Long id){
        try{
            scooterService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageDTO("El scooter fue eliminado correctamente","id eliminado: "+id,HttpStatus.OK));
        }catch(EmptyResultDataAccessException e1){
            throw new NotFoundIDException ("ID no encontrado: " + id);
        } catch (Exception e) {
            throw new RequestBadException("Error al eliminar el id " + id);
        }
    }

    // ENDPOINT PARA QUE EL MICROSERVICIO MAINTENANCE
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

    /*
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
    ) */
    @PutMapping("/inicio-mantenimiento/{id}")
    public @ResponseBody ResponseEntity<?>startMaintenance(@PathVariable(value="id")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.startMaintenance(id));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("La consulta no es correcta");
        }
    }

    //fin de mantenimiento de monopatin
   /*
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
    ) */
    @PutMapping("/fin-mantenimiento/{id}")
    public @ResponseBody ResponseEntity<?>finishMaintenance(@PathVariable(value="id")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.finishMaintenance(id));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("La consulta no es correcta");
        }
    }

    //registrar monopatin a una parda
    /*
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
    ) */
    @PutMapping("/{id}/ubicar/{id_parada}")
    public @ResponseBody ResponseEntity<?> ubicarScooterEnParada(@PathVariable(value="id")Long id,@PathVariable(value = "id_parada")Long id_parada){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.ubicarScooterEnParada(id,id_parada));
        }catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("no es posible ubicar el monopatin");
        }
    }

}
