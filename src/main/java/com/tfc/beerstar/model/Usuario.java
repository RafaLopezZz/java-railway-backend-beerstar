package com.tfc.beerstar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Usuarios", schema = "beerstar_schema")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "rol", nullable = true)
    private String rol;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Cliente cliente;
    
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Proveedor proveedor;

    public enum RolUsuario {
        USER, ADMIN, SUPERADMIN
    }

    public enum TipoUsuario {
        CLIENTE, PROVEEDOR
    }
}
