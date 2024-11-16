package org.example.microservauth.service;

import org.example.microservauth.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("AuthService")
public class AuthService {

    @Autowired
    private UserClient userClient;

    public ResponseEntity<?> getUser(Long id){
        return userClient.getUser(id);
    }
}
