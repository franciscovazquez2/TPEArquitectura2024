package org.example.microservparking.error.exception;

public class ExistException extends RuntimeException{
    public ExistException(String message) {
        super(message);
    }
}
