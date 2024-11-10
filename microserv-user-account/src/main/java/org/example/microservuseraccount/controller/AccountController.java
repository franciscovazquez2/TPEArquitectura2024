package org.example.microservuseraccount.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.BadRequestException;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.error.exception.NotExistsException;
import org.example.microservuseraccount.error.exception.RequestBadException;
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
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllAccounts() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAllAccounts());
        } catch (Exception e) {
            throw new RequestBadException("Error al listar las cuentas");
        }
    }

    // Crear cuenta
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
    @PostMapping()
    public @ResponseBody ResponseEntity<?> createAccount(@RequestBody Account newAccount) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(newAccount));
        }catch (Exception e){
            throw new RequestBadException("Fallo al crear la cuenta");
        }
    }

    // Obtener cuenta por id
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
   @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getAccount(@PathVariable(value = "id") Long id) throws NotExistsException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(id));
        } catch (BadRequestException e){
            throw new RequestBadException("Fallo al buscar el la cuenta con el ID: " +id);
        }
    }

    //anular una cuenta
   @Operation(
            summary = "Anular cuenta",
            description = "Se modifica una cuenta marcandola como inactiva",
            tags = {"Put","Account","Id"},
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
                            description = "No se puede anular la cuenta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
   @PutMapping("/anular/{id}")
    public @ResponseBody ResponseEntity<?>anularCuenta(@PathVariable(value="id")Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.anularCuenta(id));
        }catch(Exception e){
            throw new RequestBadException("error al anular la cuenta id:" + id);
        }
    }
}
