package org.example.microservscooter.error.exception;

public class ScooterMaintenanceException extends RuntimeException{

    public ScooterMaintenanceException(String message) {
        super(message);
    }
}
