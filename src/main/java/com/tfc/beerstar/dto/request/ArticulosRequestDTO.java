package com.tfc.beerstar.dto.request;

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
 * @author rafalopezzz
 */
@Data
@AllArgsConstructor
public class ArticulosRequestDTO {

    @NotBlank
    private String nombre;

    private String descripcion;

    @DecimalMin(value = "0.0")
    private Double precio;

    @Min(0)
    private Integer stock;
    
    private Long idCategoria;
    private Double graduacion;
    private String url;
}