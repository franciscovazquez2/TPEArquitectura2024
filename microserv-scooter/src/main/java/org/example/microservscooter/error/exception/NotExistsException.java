package org.example.microservscooter.error.exception;

public class NotExistsException extends RuntimeException{

    public NotExistsException(String message) {
        super(message);
    }
}
