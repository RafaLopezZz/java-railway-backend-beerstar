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

/**
 * Entidad que representa un cliente en el sistema Beerstar.
 * 
 * Esta clase está mapeada a la tabla {@code Clientes} en el esquema {@code beerstar_schema}.
 * Cada cliente está vinculado de forma única a un usuario.
 * 
 * <p>Contiene datos básicos como nombre, dirección, teléfono y la fecha en que se registró.</p>
 * 
 * <p>Se utiliza {@code @Data} de Lombok para generar automáticamente getters, setters, 
 * toString, equals y hashCode.</p>
 * 
 * @author rafalopezzz
 */
@Data
@Entity
@Table(name = "Clientes", schema = "beerstar_schema")
public class Cliente {

    /** Identificador único del cliente. Se genera automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    /**
     * Relación uno a uno con la entidad Usuario.
     * 
     * Cada cliente tiene un usuario único asociado. 
     * La columna {@code id_usuario} se marca como única para reforzar esa unicidad.
     */
    @OneToOne
    @JoinColumn(name = "id_usuario", unique = true)
    private Usuario usuario;

    /** Nombre del cliente. Campo opcional. */
    @Column(name = "nombre", nullable = true)
    private String nombre;

    /** Dirección física del cliente. */
    @Column(name = "direccion")
    private String direccion;

    /** Número de teléfono del cliente. */
    @Column(name = "telefono")
    private String telefono;

    /**
     * Fecha y hora del registro del cliente en el sistema.
     * 
     * Se inicializa automáticamente con la hora actual en el momento de creación.
     */
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro = LocalDateTime.now();
}