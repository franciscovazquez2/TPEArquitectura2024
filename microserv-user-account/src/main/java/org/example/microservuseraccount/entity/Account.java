package org.example.microservuseraccount.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
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

    public Account(){}

    public Account(long id, long cuentaMP, Date fechaAlta, double saldo) {
        this.id = id;
        this.cuentaMP = cuentaMP;
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
        this.users = new ArrayList<User>();
        this.active = true;
    }

}
