package org.example.microservbilling.error.exception;

public class NotFoundIDException extends RuntimeException{
    public NotFoundIDException(String message) {
        super(message);
    }
}
