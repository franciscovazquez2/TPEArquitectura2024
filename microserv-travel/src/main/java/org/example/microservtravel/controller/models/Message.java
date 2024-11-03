package org.example.microservtravel.controller.models;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Message {
    private String message;
    private String details;
    private HttpStatus status;

    public Message(String message, String details, HttpStatus status) {
        this.message = message;
        this.details = details;
        this.status = status;
    }
}