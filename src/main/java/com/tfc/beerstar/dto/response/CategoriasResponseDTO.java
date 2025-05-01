package com.tfc.beerstar.dto.response;

import lombok.Data;

/**
 * DTO de respuesta para representar una categoría de artículos.
 * 
 * <p>Se utiliza para enviar al cliente información sobre las categorías disponibles
 * dentro del sistema, incluyendo su identificador, nombre y descripción.</p>
 * 
 * <p>Este DTO es útil, por ejemplo, para poblar menús desplegables o listas de categorías
 * en el frontend, sin necesidad de exponer directamente la entidad JPA.</p>
 * 
 * @author rafalopezzz
 */
@Data
public class CategoriasResponseDTO {

    private Long idCategoria;
    private String nombre;
    private String descripcion;

}
