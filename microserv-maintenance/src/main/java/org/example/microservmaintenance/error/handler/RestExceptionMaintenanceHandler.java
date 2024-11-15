package org.example.microservmaintenance.error.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import org.example.microservmaintenance.error.exception.NotExistsException;
import org.example.microservmaintenance.error.dto.MessageDTO;
import org.example.microservmaintenance.error.exception.RequestBadException;
import org.example.microservmaintenance.error.exception.ScooterMaintenanceException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionMaintenanceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({NotExistsException.class})
    public ResponseEntity<?> handlerNotExistsException(NotExistsException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(MessageDTO.builder()
                                             .message(ex.getMessage())
                                             .details(request.getRequestURI())
                                             .status(HttpStatus.CONFLICT).build());
    }

    @ExceptionHandler({RequestBadException.class})
    public ResponseEntity<?> handlerBadRequestException(BadRequestException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageDTO.builder()
                        .message(ex.getMessage())
                        .details(request.getRequestURI())
                        .status(HttpStatus.BAD_REQUEST).build());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handlerBadRequestException(Exception ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageDTO.builder()
                        .message(ex.getMessage())
                        .details(request.getRequestURI())
                        .status(HttpStatus.BAD_REQUEST).build());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException ex,HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(MessageDTO.builder()
                        .message(ex.getMessage())
                        .details(request.getRequestURI())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @ExceptionHandler({ScooterMaintenanceException.class})
    public ResponseEntity<?> handlerScooterMaintenanceException(ScooterMaintenanceException ex, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(MessageDTO.builder()
                        .message(ex.getMessage())
                        .details(request.getRequestURI())
                        .status(HttpStatus.BAD_REQUEST).build());
    }

}
