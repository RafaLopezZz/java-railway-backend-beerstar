package com.rlp.ecommerce.model;

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
 * Entidad que representa un artículo o producto dentro del sistema Beerstar.
 *
 * Cada artículo está relacionado con una categoría, y contiene información
 * relevante para su visualización y comercialización, como nombre, descripción,
 * precio, stock y graduación.
 *
 * <p>
 * Está mapeada a la tabla {@code articulos} del esquema
 * {@code beerstar_schema}.</p>
 *
 * <p>
 * Se usa {@code @Data} de Lombok para generar automáticamente los métodos
 * estándar como getters, setters, equals, hashCode y toString.</p>
 *
 * @author rafalopezzz
 */
@Data
@Entity
@Table(name = "articulos", schema = "beerstar_schema")
public class Articulos {

    /**
     * Identificador único del artículo. Se genera automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private Long idArticulo;

    /**
     * El proveedor que suministra este artículo. Obligatorio: cada artículo
     * debe pertenecer a un proveedor.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private Proveedor proveedor;

    /**
     * Nombre del artículo. Este campo es obligatorio.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Descripción larga del artículo. Se permite texto extenso gracias a
     * {@code columnDefinition = "TEXT"}.
     */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Precio del artículo. Campo obligatorio.
     */
    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    /**
     * Stock disponible del artículo. Campo obligatorio.
     */
    @Column(name = "stock", nullable = false)
    private Integer stock;

    /**
     * Relación muchos a uno con la categoría del artículo. Un artículo
     * pertenece a una única categoría, pero una categoría puede tener muchos
     * artículos.
     */
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categorias categoria;

    /**
     * Graduación alcohólica del producto, si aplica (por ejemplo en cervezas).
     * Puede ser null si no es un producto con contenido alcohólico.
     */
    @Column(name = "graduacion")
    private Double graduacion;

    /**
     * URL asociada al artículo, normalmente usada para imágenes del producto.
     */
    @Column(name = "url")
    private String url;
}
