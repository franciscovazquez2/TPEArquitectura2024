package org.example.microservmaintenance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservmaintenance.dto.MaintenanceDTO;
import org.example.microservmaintenance.entity.Maintenance;
import org.example.microservmaintenance.error.exception.NotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.microservmaintenance.services.MaintenanceService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/maintenance")
@Tag(name="Maintenance", description = "Controller de mantenimiento")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    // Obtener listado de mantenimientos
    @Operation(
            summary = "Obtener mantenimientos",
            description = "Obtiene un listado de todos los mantenimientos",
            tags = {"Get","Maintenance"},
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
                            description = "Error al listar los mantenimientos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllMaintenances() {
        return ResponseEntity.status(HttpStatus.OK).body(maintenanceService.getAllMaintenances());
    }


    // Agregar mantenimiento
    @Operation(
            summary = "Crear mantenimiento",
            description = "Crea un registro de mantenimiento",
            tags = {"Post","Maintenance"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del mantenimiento a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Maintenance.class)
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
                            description = "El ID del monopatin no existe",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error al crear el mantenimiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PostMapping()
    public ResponseEntity<?> createMaintenance(@RequestBody Maintenance newMaintenance) {
            MaintenanceDTO maintenanceDTO = maintenanceService.createMaintenance(newMaintenance);
            if(maintenanceDTO==null){
                return  ResponseEntity.status(HttpStatus.CREATED).body(maintenanceDTO);
            }else{
                throw new NotExistsException("El id_scooter no existe " + newMaintenance.getIdScooter());
            }
    }

    // OBTENER UN MANTENIMIENDO CON LOS DATOS DEL MONOPATIN
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceAndScooter(@PathVariable(value = "id") Long id) {
            return ResponseEntity.status(HttpStatus.OK).body(maintenanceService.getMaintenance(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteMaintenance(@PathVariable(value="id")Long id){
        try{
            maintenanceService.deleteMaintenance(id);
            return ResponseEntity.status(HttpStatus.OK).body("Mantenimiento eliminado id: "+id);
        } catch (Exception e) {
            throw new NoSuchElementException("error al eliminar el registro");
        }
    }
}

