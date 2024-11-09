package org.example.microservmaintenance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservmaintenance.entity.Maintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.microservmaintenance.services.MaintenanceService;

@RestController
@RequestMapping("api/maintenance")
@Tag(name="Maintenance", description = "Controller de mantenimiento")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    // Obtener listado de mantenimientos
    @GetMapping
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
                            description = "Error al crear el mantenimiento",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
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

    /*
    // Obtener ciudad por id
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getCity(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cityService.getCity(id.intValue()));
        } catch (Exception e){
            String errorJson = "{\"message\": \"Error al buscar una ciudad determinada\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }
    */
}

