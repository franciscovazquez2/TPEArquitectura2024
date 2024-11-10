package org.example.microservbilling.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllFees() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(feeService.getAllFees());
        } catch (Exception e) {
            throw new RequestBadException("Error al listar las tarifas");
        }
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
    @GetMapping("/{id}")
    public ResponseEntity<?> getFeeById(@PathVariable Long id) {
        Optional<Fee> fee = feeService.getFee(id);
        if (fee.isPresent()) {
            return ResponseEntity.ok(fee.get());
        } else {
            throw new NotExistsException("Tarifa no encontrada. ID: " + id);
        }
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
    @PostMapping("/normalFee")
    public ResponseEntity<?> createNormalFee(@RequestBody Fee newFee) {
        try {
            newFee.setTipo("normal");
            return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFee(newFee));
        } catch (Exception e) {
            throw new RequestBadException("No se pudo crear la Tarifa" + newFee.toString());
        }
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
    @PostMapping("/extraFee")
    public ResponseEntity<?> createExtraFee(@RequestBody Fee newFee) {
        try {
            newFee.setTipo("extra");
            return ResponseEntity.status(HttpStatus.CREATED).body(feeService.createFee(newFee));
        } catch (Exception e) {
            throw new NotFoundException("Error al crear la tarifa extra " + newFee.toString());
        }
    }
}