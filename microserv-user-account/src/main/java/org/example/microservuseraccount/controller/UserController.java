package org.example.microservuseraccount.controller;

import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // Obtener listado de usuarios
    @GetMapping
    public @ResponseBody ResponseEntity<?> getAllUsers() {
        try {
            return
                    ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
        } catch (Exception e) {
            String errorJson = "{\"message\": \"Error al listar los usuarios\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    // Agregar mantenimiento
    @PostMapping()
    public @ResponseBody ResponseEntity<?> createUser(@RequestBody User newUser) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(newUser));
        }catch (Exception e){
            String errorJson = "{\"message\": \"Error al crear el mantenimiento\", \"details\"}";
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(errorJson);
        }
    }

    /*
    // Obtener ciudad por id
    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getCity(@PathVariable(value = "id") Long id){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(cityService.getCity(id.intValue()));
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
