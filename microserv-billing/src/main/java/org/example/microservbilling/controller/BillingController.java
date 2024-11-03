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
public class BillingController {
    @Autowired
    private BillingService billingService;

    // Obtener listado de facturas
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