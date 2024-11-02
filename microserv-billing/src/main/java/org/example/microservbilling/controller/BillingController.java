package org.example.microservbilling.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.microservbilling.services.BillingService;

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

}