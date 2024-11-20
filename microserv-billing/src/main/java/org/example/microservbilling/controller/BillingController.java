package org.example.microservbilling.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservbilling.dto.TotalFacturadoDto;
import org.example.microservbilling.entity.Billing;
import org.example.microservbilling.error.exception.NotExistsException;
import org.example.microservbilling.error.exception.RequestBadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.microservbilling.services.BillingService;
import java.util.Optional;

@RestController
@RequestMapping("api/billing")
@Tag(name = "Billing", description = "Controller de facturacion")
public class BillingController {
    @Autowired
    private BillingService billingService;

    // Obtener listado de facturas
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                [
                                                   {
                                                     "id": 1,
                                                     "fechaEmision": "2024-01-15",
                                                     "idReserva": 101,
                                                     "idUsuario": 2001,
                                                     "montoTotal": 150.75
                                                   },
                                                   {
                                                     "id": 2,
                                                     "fechaEmision": "2024-01-16",
                                                     "idReserva": 102,
                                                     "idUsuario": 2002,
                                                     "montoTotal": 120.5
                                                   }
                                                ]
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllBillings() {
        return  ResponseEntity.status(HttpStatus.OK).body(billingService.getAllBillings());
    }


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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                    {
                                                      "id": 1,
                                                      "fechaEmision": "2024-01-15",
                                                      "idReserva": 101,
                                                      "idUsuario": 2001,
                                                      "montoTotal": 150.75
                                                    }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Factura no encontrada con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                    {
                                                      "message": "no se encontro factura id: 155",
                                                      "details": "/api/billing/155",
                                                      "status": "NOT_FOUND"
                                                    }
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getBillingById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(billingService.getBilling(id));
    }

    // Crear una nueva factura
    @Operation(
            summary = "Crear factura",
            description = "Crea un registro de factura",
            tags = {"Post","Billing"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la factura a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                    example = """
                                                {
                                                  "fechaEmision": "2024-11-20",
                                                  "idReserva": 0,
                                                  "idUsuario": 0,
                                                  "montoTotal": 0
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
                                                  "fechaEmision": "2024-11-20",
                                                  "idReserva": 0,
                                                  "idUsuario": 15,
                                                  "montoTotal": 0
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear la factura",
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
                                                  "instance": "/api/billing"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<?> createBilling(@RequestBody Billing newBilling) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createBilling(newBilling));
    }


    @Operation(
            summary = "Obtener total facturado entre meses",
            description = "Obtiene un registro de facturacion entre dos meses ingresados e cierto año",
            tags = {"Get","Billing"},
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
                                                  "totalFacturado": 406.5,
                                                  "year": 2024,
                                                  "startMonth": 1,
                                                  "endMonth": 6
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    //devuelve reporte de total facturado (servicio D)
    @GetMapping("/totalFacturado/{year}/{startMonth}/{endMonth}")
    public TotalFacturadoDto reporteTotalFacturadoEnFecha(@PathVariable(value="year")int year,
                                                          @PathVariable(value="startMonth")int startMonth,
                                                          @PathVariable(value="endMonth")int endMonth){
            return billingService.reporteTotalFacturadoEnFecha(year,startMonth,endMonth);
    }


    @Operation(
            summary = "Borrar factura por id",
            description = "Borra un registro de factura mediante un id ingresado",
            tags = {"Delete","Billing","Id"},
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
                                                  "id": 5,
                                                  "fechaEmision": "2024-11-20",
                                                  "idReserva": 0,
                                                  "idUsuario": 15,
                                                  "montoTotal": 0
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Error al eliminar la factura con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "El id: 555 no Existe",
                                                  "details": "/api/billing/%7Bid%7D",
                                                  "status": "CONFLICT"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<?>deleteBilling(@RequestParam(value="id")Long id){
            return ResponseEntity.status(HttpStatus.OK).body(billingService.deleteBilling(id));
    }

}