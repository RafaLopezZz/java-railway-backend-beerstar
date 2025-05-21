package com.tfc.beerstar.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


/**
 * Entidad que representa un usuario del sistema Beerstar.
 * 
 * Esta clase se mapea a la tabla {@code Usuarios} dentro del esquema {@code beerstar_schema}.
 * Contiene los campos principales de autenticación y autorización, y establece
 * relaciones uno a uno con entidades Cliente y Proveedor, según el tipo de usuario.
 * 
 * <p>Se utiliza la anotación {@code @Data} de Lombok para generar automáticamente
 * getters, setters, equals, hashCode y toString.</p>
 * 
 * <p>Los campos {@code cliente} y {@code proveedor} se manejan con cascada total,
 * lo cual implica que las operaciones realizadas sobre Usuario en base de datos
 * (persist, remove, etc.) se propagan a estas entidades.</p>
 * 
 * @author rafalopezzz
 */
@Data
@Entity
@Table(name = "Usuarios", schema = "beerstar_schema")
public class Usuario {

    /** ID único del usuario, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;
    
    /** Email del usuario. Debe ser único y no puede ser nulo. */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Contraseña del usuario.
     * 
     * Se marca con {@code @JsonIgnore} para evitar que se serialice en respuestas JSON.
     */
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Rol asignado al usuario.
     * 
     * Puede ser {@code USER}, {@code ADMIN} o {@code SUPERADMIN}. Es opcional.
     */
    @Column(name = "rol", nullable = true)
    private String rol;

    /**
     * Tipo de usuario, obligatorio. Puede ser {@code CLIENTE} o {@code PROVEEDOR}.
     */
    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;

    /**
     * Relación uno a uno con la entidad Cliente.
     * 
     * Se utiliza {@code mappedBy = "usuario"} para indicar que la propiedad
     * de referencia está del lado de Cliente.
     * CascadeType.ALL implica que cualquier operación en Usuario
     * se propaga a Cliente.
     */
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Cliente cliente;
    
    /**
     * Relación uno a uno con la entidad Proveedor.
     * Similar al caso del Cliente.
     */
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Proveedor proveedor;

    /**
     * Enum opcional que representa los posibles roles del sistema.
     */
    public enum RolUsuario {
        USER, ADMIN, SUPERADMIN
    }

    /**
     * Enum opcional que representa los posibles tipos de usuario en el sistema.
     */
    public enum TipoUsuario {
        CLIENTE, PROVEEDOR
    }
}
