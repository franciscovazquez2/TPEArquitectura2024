package org.example.microservmaintenance.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.microservmaintenance.entity.Maintenance;
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                [
                                                  {
                                                    "id": 1,
                                                    "idScooter": 1,
                                                    "fecha_inicio": "3924-02-10T03:00:00.000+00:00",
                                                    "finalizado": true
                                                  },
                                                  {
                                                    "id": 2,
                                                    "idScooter": 2,
                                                    "fecha_inicio": "3924-02-15T03:00:00.000+00:00",
                                                    "finalizado": true
                                                  }
                                                ]
                                            """
                                    )
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
                            schema = @Schema(
                                    type = "object",
                                    additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                    example = """
                                                {
                                                    "idScooter": 50.0,
                                                    "fecha_inicio": "2024-01-01",
                                                    "finalizado": false
                                                }
                                            """
                            )
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
    public ResponseEntity<?> createMaintenance(@RequestBody @Valid Maintenance newMaintenance) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(maintenanceService.createMaintenance(newMaintenance));
    }

    // OBTENER UN MANTENIMIENDO CON LOS DATOS DEL MONOPATIN
    @Operation(
            summary = "Obtener mantenimiento por id de monopatin",
            description = "Obtiene un registro de mantenimiento mediante un id de un monopatin ingresado",
            tags = {"Get","Maintenance","Id"},
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
                            responseCode = "409",
                            description = "No se encontro mantenimiento con ese scooter",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getMaintenanceAndScooter(@PathVariable(value = "id") Long id) {
            return ResponseEntity.status(HttpStatus.OK).body(maintenanceService.getMaintenance(id));
    }

    @Operation(
            summary = "Borrar mantenimiento por id",
            description = "Borra un registro de mantenimiento mediante un id ingresado",
            tags = {"Delete","Maintenance","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(type = "string", example = "Mantenimiento eliminado id: 123")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al eliminar el mantenimiento con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?>deleteMaintenance(@PathVariable(value="id")Long id){
        try{
            maintenanceService.deleteMaintenance(id);
            return ResponseEntity.status(HttpStatus.OK).body("Mantenimiento eliminado id: "+id);
        } catch (Exception e) {
            throw new NoSuchElementException("Error al eliminar el mantenimiento con el ID ingresado");
        }
    }
}

