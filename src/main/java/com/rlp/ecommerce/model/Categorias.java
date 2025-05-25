package com.rlp.ecommerce.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad que representa una categoría de artículos en el sistema Beerstar.
 * 
 * Cada categoría puede contener múltiples artículos asociados.
 * Se encuentra mapeada a la tabla {@code categorias} del esquema {@code beerstar_schema}.
 * 
 * <p>Utiliza la anotación {@code @Data} de Lombok para generar automáticamente los
 * métodos estándar como getters, setters, equals, hashCode y toString.</p>
 * 
 * <p>Es útil para organizar productos en distintas secciones temáticas, estilos o tipos.</p>
 * 
 * @author rafalopezzz
 */
@Data
@Entity
@Table(name = "categorias", schema = "beerstar_schema")
public class Categorias {

    /** Identificador único de la categoría. Se genera automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    /**
     * Descripción larga de la categoría.
     * Se permite contenido extenso gracias a {@code columnDefinition = "TEXT"}.
     */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /**
     * Descripción larga de la categoría.
     * Se permite contenido extenso gracias a {@code columnDefinition = "TEXT"}.
     */
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Lista de artículos que pertenecen a esta categoría.
     * 
     * Relación uno a muchos: una categoría puede tener muchos artículos.
     * El atributo {@code mappedBy = "categoria"} indica que la relación es gestionada desde la entidad Articulos.
     */
    @OneToMany(mappedBy = "categoria")
    private List<Articulos> articulos;
}