package com.rlp.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de petición para crear o actualizar una categoría.
 *
 * <p>
 * Este DTO se utiliza cuando el cliente envía datos al backend para crear una
 * nueva categoría o modificar una existente. Incluye los campos mínimos
 * necesarios: nombre y una descripción opcional.</p>
 *
 * <p>
 * La validación con {@code @NotBlank} asegura que el nombre no esté vacío ni
 * compuesto solo por espacios en blanco.</p>
 *
 * @author rafalopezzz
 */
@Data
@AllArgsConstructor
public class CategoriasRequestDTO {

    /**
     * Nombre de la categoría.
     *
     * <p>
     * Debe ser único en el sistema y no puede estar vacío. Se utiliza para
     * identificar y organizar los productos.</p>
     */
    @Schema(
            description = "Nombre único de la categoría",
            example = "Cervezas Artesanales",
            required = true,
            minLength = 2,
            maxLength = 20
    )
    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    /**
     * Descripción opcional de la categoría.
     *
     * <p>
     * Proporciona información adicional sobre el tipo de productos que
     * pertenecen a esta categoría. Campo opcional.</p>
     */
    @Schema(
            description = "Descripción detallada de la categoría (opcional)",
            example = "Categoría que incluye tipos de artículos de diferentes estilos y procedencias",
            required = false,
            maxLength = 500
    )
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;

}
