package org.example.microservuseraccount.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.microservuseraccount.entity.Account;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private List<Account> accounts;
    private int rol;
}
