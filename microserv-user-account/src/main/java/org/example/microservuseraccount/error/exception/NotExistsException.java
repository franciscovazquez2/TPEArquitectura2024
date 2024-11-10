package org.example.microservuseraccount.error.exception;

public class NotExistsException extends RuntimeException{

    public NotExistsException(String message) {
        super(message);
    }
}
