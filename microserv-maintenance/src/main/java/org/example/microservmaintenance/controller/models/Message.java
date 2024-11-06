package org.example.microservmaintenance.controller.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String message;
    private String details;
    private HttpStatus status;
}
