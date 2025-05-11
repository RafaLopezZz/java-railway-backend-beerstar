package com.tfc.beerstar.dto.response;

import java.math.BigDecimal;

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
public class ArticulosResponseDTO {

    private Long idArticulo;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Long idCategoria;
    private String nombreCategoria;
    private Long idProveedor;
    private String nombreProveedor;
    private Double graduacion;
    private String url;

}
