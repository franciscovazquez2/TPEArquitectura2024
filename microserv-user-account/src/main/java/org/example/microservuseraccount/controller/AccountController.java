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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                [
                                                   {
                                                     "id": 1,
                                                     "cuentaMP": 500123456789,
                                                     "fechaAlta": "3924-02-10T03:00:00.000+00:00",
                                                     "saldo": 1000,
                                                     "active": true,
                                                     "users": [
                                                       {
                                                         "id": 1,
                                                         "nombre": "Juan",
                                                         "apellido": "Perez",
                                                         "email": "juan.perez@example.com",
                                                         "telefono": "1234567890",
                                                         "user": "juan",
                                                         "password": "$2a$10$EudYGgQcb1dJTq3bAQGoS.5peowhjLrlZQ1X.CCTsn3NaReA2YmsK",
                                                         "roles": [
                                                           {
                                                             "name": "USER"
                                                           }
                                                         ]
                                                       }
                                                     ]
                                                   },
                                                   {
                                                     "id": 2,
                                                     "cuentaMP": 500987654321,
                                                     "fechaAlta": "3924-03-15T03:00:00.000+00:00",
                                                     "saldo": 2500,
                                                     "active": true,
                                                     "users": [
                                                       {
                                                         "id": 1,
                                                         "nombre": "Juan",
                                                         "apellido": "Perez",
                                                         "email": "juan.perez@example.com",
                                                         "telefono": "1234567890",
                                                         "user": "juan",
                                                         "password": "$2a$10$EudYGgQcb1dJTq3bAQGoS.5peowhjLrlZQ1X.CCTsn3NaReA2YmsK",
                                                         "roles": [
                                                           {
                                                             "name": "USER"
                                                           }
                                                         ]
                                                       }
                                                     ]
                                                   }
                                                ]
                                            """
                                    )
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
                            schema = @Schema(
                                    type = "object",
                                    additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                    example = """
                                            {
                                              "cuentaMP": 0,
                                              "fechaAlta": "2024-11-21T00:45:18.238Z",
                                              "saldo": 0,
                                              "active": true
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
                                                  "id": 9,
                                                  "cuentaMP": 10,
                                                  "fechaAlta": "2024-11-21T00:45:18.238+00:00",
                                                  "saldo": 0,
                                                  "active": true,
                                                  "users": null
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Error al crear la cuenta",
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
                                                  "instance": "/api/account"
                                                }
                                            """
                                    )
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 1,
                                                  "cuentaMP": 500123456789,
                                                  "fechaAlta": "3924-02-10T03:00:00.000+00:00",
                                                  "saldo": 1000,
                                                  "active": true,
                                                  "users": [
                                                    {
                                                      "id": 1,
                                                      "nombre": "Juan",
                                                      "apellido": "Perez",
                                                      "email": "juan.perez@example.com",
                                                      "telefono": "1234567890",
                                                      "user": "juan",
                                                      "password": "$2a$10$EudYGgQcb1dJTq3bAQGoS.5peowhjLrlZQ1X.CCTsn3NaReA2YmsK",
                                                      "roles": [
                                                        {
                                                          "name": "USER"
                                                        }
                                                      ]
                                                    }
                                                  ]
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Error al buscar la cuenta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "java.lang.ClassNotFoundException: Provider for jakarta.ws.rs.ext.RuntimeDelegate cannot be found",
                                                  "details": "/api/account/1555",
                                                  "status": "NOT_FOUND"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
   @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getAccount(@PathVariable(value = "id") Long id) {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccount(id));
        } catch (Exception e){
            throw new RequestBadException("Fallo al buscar la cuenta con ID: " +id);
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
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "id": 9,
                                                  "cuentaMP": 10,
                                                  "fechaAlta": "2024-11-21T00:45:18.238+00:00",
                                                  "saldo": 0,
                                                  "active": false,
                                                  "users": []
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No se puede anular la cuenta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "java.lang.ClassNotFoundException: Provider for jakarta.ws.rs.ext.RuntimeDelegate cannot be found",
                                                  "details": "/api/account/anular/934",
                                                  "status": "NOT_FOUND"
                                                }
                                            """
                                    )
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

    @Operation(
            summary = "Borrar una cuenta por id",
            description = "Borra un registro de cuenta mediante un id ingresado",
            tags = {"Delete","Account","Id"},
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
                                                  
                                                }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Error al eliminar la cuenta con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            type = "object",
                                            additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                            example = """
                                                {
                                                  "message": "El id que intentas eliminar no existe ID: 9999",
                                                  "details": "/api/account/9999",
                                                  "status": "CONFLICT"
                                                }
                                            """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?>deleteAccount(@PathVariable(value = "id")Long id)throws NotExistsException{
        return ResponseEntity.status(HttpStatus.OK).body(accountService.deleteAccount(id));
    }


}
