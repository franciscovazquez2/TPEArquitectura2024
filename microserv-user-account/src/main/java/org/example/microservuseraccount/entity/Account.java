package org.example.microservuseraccount.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long cuentaMP;
    private Date fechaAlta;
    private double saldo;
    @ManyToMany (mappedBy = "accounts")
    private List<User> users;

    public Account(){}

    public Account(long id, long cuentaMP, Date fechaAlta, double saldo) {
        this.id = id;
        this.cuentaMP = cuentaMP;
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCuentaMP() {
        return cuentaMP;
    }

    public void setCuentaMP(long cuentaMP) {
        this.cuentaMP = cuentaMP;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
