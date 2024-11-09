package org.example.microservscooter.controller;

import org.example.microservscooter.controller.models.Message;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.service.ScooterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/scooter")
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
                    .body(new Message("Error al listar todos los scooter","",HttpStatus.BAD_REQUEST));
        }
    }


    @PostMapping()
    public @ResponseBody ResponseEntity<?> createTravel(@RequestBody Scooter newScooter) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(scooterService.createScooter(newScooter));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Message("Error al crear el Scooter",newScooter.toString(),HttpStatus.BAD_REQUEST));
        }
    }

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getScooter(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(scooterService.getScooter(id));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Message("Error al buscar elscooter","ID: " + id,HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> deleteByID(@PathVariable(value = "id") Long id){
        try{
            scooterService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("El scooter fue eliminado correctamente","id eliminado: "+id,HttpStatus.OK));
        }catch(EmptyResultDataAccessException e1){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Message("El id no existe","ID: " + id,HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Message("La consulta no es correcta","ID: " + id,HttpStatus.BAD_REQUEST));
        }
    }

    //	Registrar monopatín en mantenimiento (debe marcarse como no disponible para su uso)
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
