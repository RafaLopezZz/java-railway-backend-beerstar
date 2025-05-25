package com.rlp.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO de entrada para agregar artículos al carrito de compras.
 * 
 * <p>Contiene la información necesaria para añadir un artículo específico
 * al carrito del usuario autenticado, incluyendo validaciones de cantidad.</p>
 */
@Data
@Schema(
    name = "AddToCarritoRequest",
    description = "Datos requeridos para agregar un artículo al carrito de compras"
)
public class AddToCarritoRequestDTO {

    
    /**
     * Identificador único del artículo a agregar al carrito.
     * 
     * <p>Debe corresponder a un artículo existente en el catálogo
     * y que tenga stock disponible.</p>
     */
    @Schema(
        description = "ID único del artículo a agregar al carrito",
        example = "1",
        required = true,
        minimum = "1"
    )
    @NotNull(message = "El ID del artículo es obligatorio")
    private Long idArticulo;

    /**
     * Cantidad del artículo a agregar al carrito.
     * 
     * <p>Debe ser un número entero positivo mayor a 0.
     * Se validará contra el stock disponible del artículo.</p>
     */
    @Schema(
        description = "Cantidad del artículo a agregar (debe ser mayor a 0)",
        example = "2",
        required = true,
        minimum = "1",
        maximum = "999"
    )
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Max(value = 999, message = "La cantidad no puede exceder 999 unidades")
    private Integer cantidad;

}
