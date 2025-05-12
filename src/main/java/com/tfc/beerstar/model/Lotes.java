package com.tfc.beerstar.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa un lote de productos ofrecido por un proveedor.
 * 
 * Esta clase está mapeada a la tabla {@code lotes} dentro del esquema {@code beerstar_schema}.
 * Un lote contiene información básica como nombre, descripción, precio y una URL.
 * 
 * <p>Está relacionado con un proveedor mediante una relación {@code @ManyToOne},
 * lo que significa que un proveedor puede tener muchos lotes.</p>
 * 
 * <p>Se utiliza Lombok {@code @Data} para generar automáticamente los métodos
 * estándar (getters, setters, toString, equals, hashCode).</p>
 * 
 * @author rafalopezzz
 */
@Entity
@Table(name = "lotes", schema = "beerstar_schema")
@Data
public class Lotes {

    /** Identificador único del lote. Se genera automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_lote")
    private Long idLote;

    /** Nombre del lote. Este campo es obligatorio. */
    @Column(name = "nombre_lote", nullable = false)
    private String nombreLote;

    /**
     * Descripción detallada del lote.
     * Se utiliza {@code columnDefinition = "TEXT"} para permitir textos largos en la base de datos.
     */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Relación muchos a uno con el proveedor.
     * Un proveedor puede tener múltiples lotes, pero cada lote pertenece a un único proveedor.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor")
    private Proveedor proveedor;

    /** Precio del lote. Campo obligatorio. */
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    /** URL asociada al lote. Puede ser una imagen, ficha de producto, etc. */
    @Column(name = "url")
    private String url;

}
