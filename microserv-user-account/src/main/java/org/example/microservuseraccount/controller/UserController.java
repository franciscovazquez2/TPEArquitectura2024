package org.example.microservuseraccount.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.ws.rs.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.microservuseraccount.client.ScooterClient;
import org.example.microservuseraccount.client.TravelClient;
import org.example.microservuseraccount.dto.ScooterDTO;
import org.example.microservuseraccount.dto.TravelDto;
import org.example.microservuseraccount.dto.UserCreateDTO;
import org.example.microservuseraccount.dto.UserTokenDto;
import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.error.exception.NotExistsException;
import org.example.microservuseraccount.error.exception.RequestBadException;
import org.example.microservuseraccount.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/user")
@Tag(name = "User", description = "Controller de usuario")
public class UserController {

    @Autowired
    private UserService userService;



    // Obtener listado de usuarios
    @Operation(
            summary = "Obtener usuarios",
            description = "Obtiene un listado de todos los usuarios",
            tags = {"Get","User"},
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
                                                        "id": 4,
                                                        "nombre": "Juan",
                                                        "apellido": "Perez",
                                                        "email": null,
                                                        "telefono": "1234567890",
                                                        "accounts": [
                                                          {
                                                            "id": 2,
                                                            "cuentaMP": 500987654321,
                                                            "fechaAlta": "3924-03-15T03:00:00.000+00:00",
                                                            "saldo": 2500,
                                                            "active": true
                                                          },
                                                          {
                                                            "id": 3,
                                                            "cuentaMP": 500555666777,
                                                            "fechaAlta": "3924-04-01T03:00:00.000+00:00",
                                                            "saldo": 3200,
                                                            "active": true
                                                          }
                                                        ],
                                                        "rol": [
                                                          {
                                                            "name": "USER"
                                                          },
                                                          {
                                                            "name": "USER"
                                                          }
                                                        ]
                                                      },
                                                      {
                                                        "id": 5,
                                                        "nombre": "Carlos",
                                                        "apellido": "Diaz",
                                                        "email": null,
                                                        "telefono": "5556667777",
                                                        "accounts": [],
                                                        "rol": [
                                                          {
                                                            "name": "USER"
                                                          }
                                                        ]
                                                      }
                                                ]
                                            """
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllUsers() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        } catch (BadRequestException e) {
        throw new RequestBadException("Error al querer listar todos los usuarios");
        }
    }

    // Crear usuario
    @Operation(
        summary = "Crear usuario",
        description = "Crea un registro de usuario",
        tags = {"Post","User"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del usuario a crear",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                type = "object",
                                additionalProperties = Schema.AdditionalPropertiesValue.FALSE,
                                example = """
                                            {
                                              "nombre": "Juan",
                                              "apellido": "Perez",
                                              "email": "juanperez@gmail.com",
                                              "telefono": "123456789",
                                              "user": "JuanPerez",
                                              "password": "12345",
                                              "roles": [
                                                {
                                                  "name": "USER"
                                                }
                                              ]
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
                                schema = @Schema(implementation = ResponseEntity.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Error al crear el usuario",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(type = "object")
                        )
                )
        }
    )
    @PostMapping()
    public @ResponseBody ResponseEntity<?> createUser(@RequestBody UserCreateDTO newUser) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(newUser));
        }catch (BadRequestException e){
            throw new RequestBadException("Error al crear el usuario");
        }
    }

    //asocia una cuenta a un usuario
    @Operation(
            summary = "Asociar una cuenta a un usuario mediante id de usuario y id de cuenta",
            description = "Asocia una cuenta a un usuario mediante los id ingresados",
            tags = {"Put","User","Id"},
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
                            description = "No se puede asociar cuenta",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @PutMapping("/asociarCuenta/{userId}/{accountId}")
    public @ResponseBody ResponseEntity<?> asociarCuenta (@PathVariable(value = "userId")Long userId,
                                                        @PathVariable(value = "accountId")Long accountId) throws NotExistsException {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.asociarCuenta(userId,accountId));
        }catch(BadRequestException e){
            throw new RequestBadException("Fallo al querer asociar una cuenta al usuario");
        }
    }

    // Obtener usuario por id
    @Operation(
            summary = "Obtener usuario por id",
            description = "Obtiene un registro de usuario mediante un id ingresado",
            tags = {"Get","User","Id"},
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
                            description = "Error al buscar el usuario",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getUser(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
        } catch (BadRequestException e){
        throw new RequestBadException("Error al buscar el usuario con ID: " + id);
        }
    }

    @Operation(
            summary = "Borrar usuario por id",
            description = "Borra un registro de usuario mediante un id ingresado",
            tags = {"Delete","User","Id"},
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
                            description = "Error al eliminar el usuario con el ID ingresado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(type = "object")
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<?>deleteUser(@PathVariable(value = "id")Long id)throws NotExistsException {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(id));
    }

    @Hidden
    @GetMapping("/auth/{username}")
    public UserTokenDto getUserByUsername(@PathVariable(name= "username") String username) {
        try {
        return userService.findOneWithAuthoritiesByUsernameIgnoreCase(username);

        } catch (Exception e) {
            throw new NotExistsException("lo detecta como id pero es: " + username);
        }
    }

    //iniciar viaje
    @PostMapping("/iniciarViaje/{id_user}/{id_scooter}")
    public TravelDto inciarViaje(@RequestParam(value="id_scooter")Long id_user, @RequestParam(value="id_scooter")Long id_scooter){

            return userService.iniciarViaje(id_user,id_scooter);
    }
}
