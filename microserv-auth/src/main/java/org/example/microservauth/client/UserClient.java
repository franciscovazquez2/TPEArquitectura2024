package org.example.microservauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microserv-user-account")
public interface UserClient {

    @GetMapping("api/user/{id}")
    ResponseEntity<?> getUser(@PathVariable(value = "id") Long id);
}
