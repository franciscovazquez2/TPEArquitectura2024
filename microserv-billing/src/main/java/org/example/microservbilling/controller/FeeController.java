package org.example.microservbilling.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservbilling.entity.Fee;
import org.example.microservbilling.error.exception.NotExistsException;
import org.example.microservbilling.error.exception.NotFoundException;
import org.example.microservbilling.error.exception.RequestBadException;
import org.example.microservbilling.services.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("api/fee")
@Tag(name="Fee", description = "Controller de tarifa")
public class FeeController {
    @Autowired
    private FeeService feeService;

    // Obtener listado de tarifas
    @Operation(
            summary = "Obtener tarifas",
            description = "Obtiene un listado de todas las tarifas",
            tags = {"Get","Fee"},
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
                                                    "monto": 50,
                                                    "fechaInicio": "2024-06-30",
                                                    "tipo": "normal"
                                                  },
                                                  {
                                                    "id": 2,
                                                    "monto": 75,
                                                    "fechaInicio": "2024-07-01",
                                                    "tipo": "extra"
                                                  }
                                                ]
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllFees() {
        return ResponseEntity.status(HttpStatus.OK).body(feeService.getAllFees());
    }


    @Operation(
            summary = "Obtener tarifa por id",
            description = "Obtiene un registro de tarifa mediante un id ingresado",
            tags = {"Get","Fee","Id"},
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
                                                    {
                                                      "id": 1,
                                                      "monto": 50,
                                                      "fechaInicio": "2024-06-30",
                                                      "tipo": "normal"
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Tarifa no encontrada con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                    {
                                                      "message": "El id: 15555 No existe",
                                                      "details": "/api/fee/15555",
                                                      "status": "CONFLICT"
                                                    }
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getFeeById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(feeService.getFee(id));
    }

    //crea tarifa (se controla el tipo de tarifa)
    @Operation(
            summary = "Crear tarifa normal",
            description = "Crea un registro de tarifa de tipo normal",
            tags = {"Post","Fee"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la tarifa a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                    example = """
                                                {
                                                  "monto": 10
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 5,
                                                  "monto": 10,
                                                  "fechaInicio": "2024-11-20",
                                                  "tipo": "normal"
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "No se pudo crear la tarifa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "type": "about:blank",
                                                  "title": "Bad Request",
                                                  "status": 400,
                                                  "detail": "Failed to read request",
                                                  "instance": "/api/fee/normalFee"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @PostMapping("/normalFee")
    public ResponseEntity<?> createNormalFee(@RequestBody Fee newFee) {
        newFee.setFechaInicio(LocalDate.now());
        newFee.setTipo("normal");
        return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFee(newFee));
    }

    @Operation(
            summary = "Crear tarifa extra",
            description = "Crea un registro de tarifa de tipo extra",
            tags = {"Post","Fee"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la tarifa a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                    example = """
                                                {
                                                  "monto": 50
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 6,
                                                  "monto": 50,
                                                  "fechaInicio": "2024-11-20",
                                                  "tipo": "extra"
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear la tarifa extra",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "type": "about:blank",
                                                  "title": "Bad Request",
                                                  "status": 400,
                                                  "detail": "Failed to read request",
                                                  "instance": "/api/fee/normalFee"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @PostMapping("/extraFee")
    public ResponseEntity<?> createExtraFee(@RequestBody Fee newFee) {
        newFee.setTipo("extra");
        newFee.setFechaInicio(LocalDate.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFee(newFee));
    }


    @Operation(
            summary = "Crear tarifa activa desde una fecha",
            description = "Crea una tarifa que sera habilitada a partir de cierta fecha",
            tags = {"Post","Fee"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la tarifa a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                    example = """
                                                {
                                                    "monto": 50.0,
                                                    "fechaInicio": "2024-01-01",
                                                    "tipo": "normal"
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 10,
                                                  "monto": 8,
                                                  "fechaInicio": "2024-12-12",
                                                  "tipo": "normal"
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear la tarifa extra",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "type": "about:blank",
                                                  "title": "Bad Request",
                                                  "status": 400,
                                                  "detail": "Failed to read request",
                                                  "instance": "/api/fee/feePriceSince"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @PostMapping("/feePriceSince")
    public ResponseEntity<?> createFeeSinceDate(@RequestBody Fee newFee){
        return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFee(newFee));
    }


    @Operation(
            summary = "Borrar tarifa por id",
            description = "Borra un registro de tarifa mediante un id ingresado",
            tags = {"Delete","Fee","Id"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful request",
                            content = @Content(
                                    mediaType = "text/plain",
                                    schema = @Schema(type = "string", example = "Tarifa eliminada: 123")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Error al eliminar la tarifa con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "El id que quieres eliminar no existe. ID: 955",
                                                  "details": "/api/fee/%7Bid%7D",
                                                  "status": "CONFLICT"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?>deleteFee(@PathVariable(value="id")Long id){
        feeService.delteFee(id);
        return ResponseEntity.status(HttpStatus.OK).body("Tarifa eliminada: "+id);
    }
}