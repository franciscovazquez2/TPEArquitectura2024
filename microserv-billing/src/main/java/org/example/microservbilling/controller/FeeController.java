package org.example.microservbilling.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservbilling.entity.Fee;
import org.example.microservbilling.services.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("api/fee")
@Tag(name="Fee", description = "Controller de tarifa")
public class FeeController {
    @Autowired
    private FeeService feeService;

    // Obtener listado de tarifas
    @GetMapping
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
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al listar las tarifas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    public @ResponseBody ResponseEntity<?> getAllFees() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(feeService.getAllFees());
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al listar las tarifas\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }


    @GetMapping("/{id}")
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
                                    schema = @Schema(implementation = ResponseEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al buscar tarifa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    public ResponseEntity<?> getFeeById(@PathVariable Long id) {
        Optional<Fee> fee = feeService.getFee(id);
        if (fee.isPresent()) {
            return ResponseEntity.ok(fee.get());
        } else {
            String errorJson = "{\"message\": \"Tarifa no encontrada\"}";
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    @PostMapping
    @Operation(
            summary = "Crear tarifa",
            description = "Crea un registro de tarifa",
            tags = {"Post","Fee"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la tarifa a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Fee.class)
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
                            description = "Error al crear la tarifa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    public ResponseEntity<?> createFee(@RequestBody Fee newFee) {
        try {
            Fee savedFee = feeService.createFee(newFee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFee);
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al crear la tarifa\", \"details\": \"" + e.getMessage() + "\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }
}