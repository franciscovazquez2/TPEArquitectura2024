package org.example.microservtravel.error.exception;


import org.apache.coyote.BadRequestException;

public class RequestBadException extends BadRequestException {
    public RequestBadException(String message) {
        super(message);
    }
}
