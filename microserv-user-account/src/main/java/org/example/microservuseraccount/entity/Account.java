package org.example.microservuseraccount.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long cuentaMP;
    private Date fechaAlta;
    private double saldo;
    private boolean active;
    @ManyToMany (mappedBy = "accounts")
    @JsonIgnore
    private List<User> users;

    //necesario para csv
    public Account(long cuentaMP, Date fechaAlta, double saldo, boolean active) {
        this.cuentaMP = cuentaMP;
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
        this.active = active;
    }

    public void addUser(User user){
        this.users.add(user);
    }
}
