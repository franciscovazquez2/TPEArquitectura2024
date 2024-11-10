package org.example.microservmaintenance.controller;

import org.example.microservmaintenance.error.dto.MessageDTO;
import org.example.microservmaintenance.dto.MaintenanceDTO;
import org.example.microservmaintenance.entity.Maintenance;
import org.example.microservmaintenance.error.exception.NotExistsException;
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
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(MessageDTO.builder().message("Error al listar los mantenimientos")
                                           .details("GET all")
                                           .status(HttpStatus.BAD_REQUEST)
                                           .build());
        }
    }

    // Agregar mantenimiento
    @PostMapping()
    public ResponseEntity<?> createMaintenance(@RequestBody Maintenance newMaintenance) {
        try {
            MaintenanceDTO maintenanceDTO = maintenanceService.createMaintenance(newMaintenance);
            if(!maintenanceDTO.isEmtpy()){
                return  ResponseEntity.status(HttpStatus.CREATED).body(maintenanceDTO);
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO.builder().message("No se encontro scooter. ").details("ID: " + newMaintenance.getIdScooter()).status(HttpStatus.CONFLICT).build());
            }
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(MessageDTO.builder().message("Error al crear el mantenimiento")
                                           .details("POST : " + newMaintenance.toString())
                                           .status(HttpStatus.BAD_REQUEST)
                                           .build());
        }
    }

    // OBTENER UN MANTENIMIENDO CON LOS DATOS DEL MONOPATIN
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceAndScooter(@PathVariable(value = "id") Long id) throws NotExistsException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(maintenanceService.getMaintenance(id));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(MessageDTO.builder().message("No se encontro el ID Solicitado")
                                           .status(HttpStatus.BAD_REQUEST)
                                           .build());
        }
    }

}

