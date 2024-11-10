package org.example.microservtravel.error.exception;

public class NotExistsException extends RuntimeException{

    public NotExistsException(String message) {
        super(message);
    }
}
