package org.example.microservuseraccount.controller;

import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Obtener listado de cuentas
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllAccounts() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al listar las cuentas\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    // Agregar cuenta
    @PostMapping()
    public @ResponseBody ResponseEntity<?> createAccount(@RequestBody Account newAccount) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(newAccount));
        }catch (Exception e){
            String errorJson = "{\"message\": \"Error al crear la cuenta\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    /*
    // Obtener cuenta por id
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getCity(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(id.intValue()));
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
