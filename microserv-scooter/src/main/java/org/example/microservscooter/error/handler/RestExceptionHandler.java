package org.example.microservscooter.error.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import org.example.microservscooter.error.dto.MessageDTO;
import org.example.microservscooter.error.exception.ScooterMaintenanceException;
import org.example.microservscooter.error.exception.NotExistsException;
import org.example.microservscooter.error.exception.NotFoundException;
import org.example.microservscooter.error.exception.RequestBadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotExistsException.class})
    public ResponseEntity<?> handlerNotExistsException(NotExistsException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .body(MessageDTO.builder()
                                             .message(ex.getMessage())
                                             .details(request.getRequestURI())
                                             .status(HttpStatus.NO_CONTENT).build());
    }

    @ExceptionHandler({RequestBadException.class})
    public ResponseEntity<?> handlerBadRequestException(BadRequestException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageDTO.builder()
                        .message(ex.getMessage())
                        .details(request.getRequestURI())
                        .status(HttpStatus.BAD_REQUEST).build());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handlerNotFoundIDException(NotFoundException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(MessageDTO.builder()
                        .message(ex.getMessage())
                        .details(request.getRequestURI())
                        .status(HttpStatus.NOT_FOUND).build());
    }

    @ExceptionHandler({ScooterMaintenanceException.class})
    public ResponseEntity<?> handlerScooterMaintenanceException(ScooterMaintenanceException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(MessageDTO.builder()
                        .message(ex.getMessage())
                        .details(request.getRequestURI())
                        .status(HttpStatus.CONFLICT).build());
    }

}
