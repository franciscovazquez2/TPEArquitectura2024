package org.example.microservuseraccount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.microservuseraccount.entity.Authority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDTO {
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String user;
    private String password;
    private Authority rol;
}
