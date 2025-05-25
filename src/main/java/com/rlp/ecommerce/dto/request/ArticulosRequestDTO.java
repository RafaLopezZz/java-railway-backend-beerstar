package com.rlp.ecommerce.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO (Data Transfer Object) para recibir datos de creación o actualización de un artículo.
 *
 * <p>Este objeto encapsula la información enviada por el cliente cuando se crea o actualiza un artículo
 * en la base de datos. Se utiliza principalmente en los controladores para recibir y validar entradas.</p>
 *
 * <p>Incluye validaciones con anotaciones como {@code @NotBlank}, {@code @DecimalMin}, y {@code @Min}
 * para garantizar que los datos cumplan ciertas condiciones básicas antes de procesarlos.</p>
 * 
 * Ejemplo de uso en una petición JSON - crear artículo - nombre(NotNull)
 * <pre>
 *  {
 *   "nombre": "Orval",
 *   "descripcion": "Este es un tipo de cerveza de fermentación alta, que incluye subestilos como Pale Ale, India Pale Ale (IPA), Stout, Porter, y Belgian Ale.",
 *   "precio": 2.95,
 *   "stock": 10,
 *   "idCategoria": 1,
 *   "nombreCategoria": "Ale",
 *   "idProveedor": 1,
 *   "nombreProveedor": "Proveedor Genérico",
 *   "graduacion": 6.2,
 *   "url": null
 *   }
 * </pre>
 *
 * @author rafalopezzz
 */
@Data
@AllArgsConstructor
@Schema(description = "Datos para la creación o actualización de un artículo")
public class ArticulosRequestDTO {

    @NotBlank
    @Schema(description = "Nombre del artículo", example = "Estrella de Levante", required = true)
    private String nombre;

    @Schema(description = "Descripción detallada del artículo", example = "Cerveza artesanal de tipo rubia con 5% de alcohol")
    private String descripcion;

    @DecimalMin(value = "0.0")
    @Schema(description = "Precio del artículo", example = "4.99", required = true)
    private BigDecimal precio;

    @Min(0)
    @Schema(description = "Cantidad disponible en stock", example = "50", required = true)
    private Integer stock;
    
    @Schema(description = "ID de la categoría a la que pertenece el artículo", example = "1", required = true)
    private Long idCategoria;

    @Schema(description= "ID del proveedor del artículo", example = "1", required = true)
    private long idProveedor;

    @Schema(description= "Graduación del artículo", example = "5.0")
    private Double graduacion;

    @Schema(description= "URL de la imagen del artículo", example = "https://example.com/imagen.jpg")
    private String url;
}