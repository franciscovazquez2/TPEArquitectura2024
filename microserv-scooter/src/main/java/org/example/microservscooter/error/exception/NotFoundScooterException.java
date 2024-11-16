package org.example.microservscooter.error.exception;

public class NotFoundScooterException extends RuntimeException{
    public NotFoundScooterException(String message) {
        super(message);
    }
}
