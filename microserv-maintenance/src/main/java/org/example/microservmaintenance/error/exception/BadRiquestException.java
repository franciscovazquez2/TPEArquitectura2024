package org.example.microservmaintenance.error.exception;

import jakarta.ws.rs.BadRequestException;

public class BadRiquestException extends BadRequestException {
    public BadRiquestException(String message) {
        super(message);
    }
}
