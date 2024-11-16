package org.example.microservauth.controller;

import org.example.microservauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<?> getAuthentication(@PathVariable Long id){
        return authService.getUser(id);
    }
}
