package org.example.microservmaintenance.controller;

import org.example.microservmaintenance.controller.models.Message;
import org.example.microservmaintenance.entity.Maintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.microservmaintenance.services.MaintenanceService;

@RestController
@RequestMapping("api/maintenance")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    // Obtener listado de mantenimientos
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllMaintenances() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(maintenanceService.getAllMaintenances());
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al listar los mantenimientos\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    // Agregar mantenimiento
    @PostMapping()
    public @ResponseBody ResponseEntity<?> createMaintenance(@RequestBody Maintenance newMaintenance) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.createMaintenance(newMaintenance));
        }catch (Exception e){
            String errorJson = "{\"message\": \"Error al crear el mantenimiento\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    // OBTENER UN MANTENIMIENDO CON LOS DATOS DEL MONOPATIN
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getMaintenanceAndScooter(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(maintenanceService.getMaintenance(id));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Message.builder().message("No se encontro el ID Solicitado").details("Id incorrecto").status(HttpStatus.BAD_REQUEST).build());
        }
    }

}

