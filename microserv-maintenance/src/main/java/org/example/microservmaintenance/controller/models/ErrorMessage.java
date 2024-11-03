package org.example.microservmaintenance.controller.models;

import lombok.Data;

@Data
public class ErrorMessage {
    private String message;
    private String details;
    private int status;

    public ErrorMessage(String message, String details, int status) {
        this.message = message;
        this.details = details;
        this.status = status;
    }
}
