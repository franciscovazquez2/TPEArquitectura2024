package org.example.microservuseraccount.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String user;
    private String password;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Account> accounts;
    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(
            name = "usuario_roles", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "usuario_id"), // Clave foránea de Usuario
            inverseJoinColumns = @JoinColumn(name = "rol_name") // Clave foránea de Authority
    )
    private List<Authority> roles;
    //necesario para el csv
    public User(String nombre, String apellido, String email, String telefono,String user, String password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.user=user;
        this.password=password;
        this.accounts = new ArrayList<>();
        this.roles = new ArrayList<>();
    }

    public void addAccount(Account newAccount){
        this.accounts.add(newAccount);
    }
    public void addRol(Authority authority){this.roles.add(authority);}
}
