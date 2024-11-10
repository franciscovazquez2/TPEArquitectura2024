package org.example.microservparking.error.exception;

public class NotExistsException extends RuntimeException{

    public NotExistsException(String message) {
        super(message);
    }
}
