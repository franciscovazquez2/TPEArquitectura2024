package org.example.microservuseraccount.error.exception;

public class NotFoundUserAccountException extends RuntimeException{
    public NotFoundUserAccountException(String message) {
        super(message);
    }
}
