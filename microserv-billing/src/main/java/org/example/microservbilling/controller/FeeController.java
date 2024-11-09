package org.example.microservbilling.controller;
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

public class FeeController {
    @Autowired
    private FeeService feeService;

    // Obtener listado de tarifas
    @GetMapping
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

    //crea tarifa (se controla el tipo de tarifa)
    @PostMapping
    public ResponseEntity<?> createFee(@RequestBody Fee newFee) {
        try {
            Fee savedFee = feeService.createFee(newFee);
            if(newFee.getTipo().equals("normal")|| newFee.getTipo().equals("extra")){
                return ResponseEntity.status(HttpStatus.CREATED).body(savedFee);
            }
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("tipo de tarifa invalido");

        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al crear la tarifa\", \"details\": \"" + e.getMessage() + "\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }
}