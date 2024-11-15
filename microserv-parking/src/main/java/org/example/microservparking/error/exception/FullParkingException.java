package org.example.microservparking.error.exception;

public class FullParkingException extends RuntimeException{
    public FullParkingException(String message) {
        super(message);
    }
}
