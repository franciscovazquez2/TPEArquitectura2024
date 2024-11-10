package org.example.microservbilling.controller;
import org.example.microservbilling.entity.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.microservbilling.services.BillingService;
import java.util.Optional;

@RestController
@RequestMapping("api/billing")
//@Tag(name = "Billing", description = "Controller de facturacion")
public class BillingController {
    @Autowired
    private BillingService billingService;

    // Obtener listado de facturas
   /*
    @Operation(
            summary = "Obtener facturas",
            description = "Obtiene un listado de todas las facturas",
            tags = {"Get","Billing"},
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
                            description = "Error al listar las facturas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )*/
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllBillings() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(billingService.getAllBillings());
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al listar las facturas\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }


    /*
    @Operation(
            summary = "Obtener factura por id",
            description = "Obtiene un registro de factura mediante un id ingresado",
            tags = {"Get","Billing","Id"},
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
                            description = "Error al buscar factura",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )*/
    @GetMapping("/{id}")
    public ResponseEntity<?> getBillingById(@PathVariable Long id) {
        Optional<Billing> billing = billingService.getBilling(id);
        if (billing.isPresent()) {
            return ResponseEntity.ok(billing.get());
        } else {
            String errorJson = "{\"message\": \"Factura no encontrada\"}";
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    // Crear una nueva factura
    /*
    @Operation(
            summary = "Crear factura",
            description = "Crea un registro de factura",
            tags = {"Post","Billing"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la factura a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Billing.class)
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
                            description = "Error al crear la factura",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
     */
    @PostMapping
    public ResponseEntity<?> createBilling(@RequestBody Billing newBilling) {
        try {
            Billing savedBilling = billingService.createBilling(newBilling);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBilling);
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al crear la factura\", \"details\": \"" + e.getMessage() + "\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }
}