package org.example.microservtravel.controller;
import org.example.microservtravel.controller.models.Message;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/travel")
public class TravelController {

    @Autowired
    private TravelService travelService;


    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllTravels() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(travelService.getAllTravels());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Message("Error al listar los viajes","",HttpStatus.BAD_REQUEST));
        }
    }

    @PostMapping()
    public @ResponseBody ResponseEntity<?> createTravel(@RequestBody Travel newTravel) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(travelService.createTravel(newTravel));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Message("Error al crear el viaje","",HttpStatus.BAD_REQUEST));
        }
    }


    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getTravel(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(travelService.getTravel(id));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new Message("Error al buscar el viaje","ID: " + id,HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?> deleteByID(@PathVariable(value = "id") Long id){
        try{
            travelService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("El viaje fue eliminado correctamente","id eliminado: "+id,HttpStatus.OK));
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

}
