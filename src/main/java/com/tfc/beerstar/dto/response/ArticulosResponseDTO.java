package com.tfc.beerstar.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO de respuesta para representar los datos de un artículo.
 * 
 * <p>Se utiliza para enviar al cliente la información detallada de un artículo,
 * incluyendo los datos básicos, su categoría asociada y otros atributos útiles.</p>
 * 
 * <p>Este DTO es especialmente útil para mostrar artículos en vistas de lista o detalle,
 * proporcionando información tanto del propio artículo como de su categoría.</p>
 * 
 * @author rafalopezzz
 */
@Data
@Schema(description = "Datos de respuesta de un artículo")
public class ArticulosResponseDTO {

    @Schema(description = "ID del artículo", example = "1")
    private Long idArticulo;

    @Schema(description = "Nombre del artículo", example = "Estrella de Levante")
    private String nombre;

    @Schema(description = "Descripción del artículo", example = "Cerveza artesanal de tipo rubia con 5% de alcohol")
    private String descripcion;

    @Schema(description = "Precio del artículo", example = "4.99")
    private BigDecimal precio;

    @Schema(description = "Cantidad disponible en stock", example = "50")
    private Integer stock;

    @Schema(description = "ID de la categoría a la que pertenece el artículo", example = "1")
    private Long idCategoria;

    @Schema(description = "Nombre de la categoría a la que pertenece el artículo", example = "Ale")
    private String nombreCategoria;

    @Schema(description = "ID del proveedor del artículo", example = "1")
    private Long idProveedor;

    @Schema(description = "Nombre del proveedor del artículo", example = "Proveedor Genérico")
    private String nombreProveedor;

    @Schema(description = "Graduación alcohólica del artículo", example = "6.2")
    private Double graduacion;
    
    @Schema(description = "URL de la imagen del artículo", example = "https://example.com/imagen.jpg")
    private String url;

}
