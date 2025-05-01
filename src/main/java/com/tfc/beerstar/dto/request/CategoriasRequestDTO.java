package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de petición para crear o actualizar una categoría.
 *
 * <p>Este DTO se utiliza cuando el cliente envía datos al backend para crear
 * una nueva categoría o modificar una existente. Incluye los campos mínimos
 * necesarios: nombre y una descripción opcional.</p>
 *
 * <p>La validación con {@code @NotBlank} asegura que el nombre no esté vacío
 * ni compuesto solo por espacios en blanco.</p>
 * 
 * @author rafalopezzz
 */
@Data
@AllArgsConstructor
public class CategoriasRequestDTO {

    @NotBlank
    private String nombre;
    private String descripcion;

}
