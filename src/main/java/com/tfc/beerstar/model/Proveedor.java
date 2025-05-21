package com.tfc.beerstar.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Entidad que representa a un proveedor en el sistema Beerstar.
 * 
 * Esta clase está mapeada a la tabla {@code Proveedores} del esquema {@code beerstar_schema}.
 * 
 * <p>Cada proveedor está vinculado a un usuario mediante una relación uno a uno.</p>
 * 
 * <p>Incluye información como nombre, dirección, teléfono, fecha de registro y una URL (que puede ser su sitio web, perfil, etc.).</p>
 * 
 * <p>Se usa Lombok para generar automáticamente getters, setters, toString, equals y hashCode.</p>
 * 
 * @author rafalopezzz
 */
@Data
@Entity
@Table(name = "Proveedores", schema = "beerstar_schema")
public class Proveedor {

    /** Identificador único del proveedor. Se genera automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProveedor;

    /**
     * Relación uno a uno con la entidad Usuario.
     * 
     * El proveedor está asociado a un único usuario. La columna {@code id_usuario} es única,
     * lo que garantiza que un mismo usuario no pueda ser proveedor más de una vez.
     */
    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Usuario usuario;

    /** Nombre del proveedor. Opcional. */
    @Column(name = "nombre", nullable = true)
    private String nombre;

    /** Dirección física del proveedor. */
    @Column(name = "direccion")
    private String direccion;

    /** Número de teléfono del proveedor. */
    @Column(name = "telefono")
    private String telefono;

    /**
     * Fecha y hora en la que el proveedor se registró.
     * 
     * Se inicializa automáticamente con la fecha y hora actual.
     */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    /**
     * URL asociada al proveedor. Puede ser su sitio web, tienda online, etc.
     */
    @Column(name = "url")
    private String url;
}