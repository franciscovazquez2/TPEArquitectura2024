package org.example.microservparking.error.exception;

public class ParkingFullExection extends RuntimeException{
    public ParkingFullExection(String message) {
        super(message);
    }
}
