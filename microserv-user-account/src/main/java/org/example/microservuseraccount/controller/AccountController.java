package org.example.microservuseraccount.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/account")
@Tag(name = "Account", description = "Controller de cuenta")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Obtener listado de cuentas
    @GetMapping
    @Operation(
            summary = "Obtener cuentas",
            description = "Obtiene un listado de todos las cuentas",
            tags = {"Get","Account"},
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
                            description = "Error al listar las cuentas",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    public @ResponseBody ResponseEntity<?> getAllAccounts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
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
    @Operation(
            summary = "Crear cuenta",
            description = "Crea un registro de cuenta",
            tags = {"Post","Account"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la cuenta a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Account.class)
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
                            description = "Error al crear la cuenta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
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


    // Obtener cuenta por id
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener cuenta por id",
            description = "Obtiene un registro de cuenta mediante un id ingresado",
            tags = {"Get","Account","Id"},
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
                            description = "Error al buscar la cuenta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    public @ResponseBody ResponseEntity<?> getAccount(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(id));
        } catch (Exception e){
            String errorJson = "{\"message\": \"Error al buscar la cuenta\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

}
