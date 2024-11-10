package org.example.microservuseraccount.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.microservuseraccount.entity.User;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {

    private long id;
    private long cuentaMP;
    private Date fechaAlta;
    private double saldo;
    private boolean active;
    private List<User> users;

}
