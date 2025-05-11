package com.tfc.beerstar.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de petición que representa los datos necesarios para crear o actualizar un lote.
 *
 * <p>Este objeto se utiliza para representar un lote de productos en el sistema,
 * normalmente como parte de una petición para registrar nuevos lotes o modificar
 * los existentes. El lote está asociado a un proveedor y tiene atributos como
 * nombre, descripción, precio, entre otros.</p>
 *
 * Ejemplo de uso en una petición JSON:
 * <pre>
 * {
 *   "nombreLote": "Lote Primavera 2025",
 *   "descripcion": "Lote de cervezas artesanales de temporada",
 *   "idProveedor": 7,
 *   "precio": 19.99,
 *   "url": "https://cervezasartesanales.com/lote-primavera-2025"
 * }
 * </pre>
 * 
 * @author rafalopezzz
 */
@Data
@AllArgsConstructor
public class LoteRequestDTO {

    @NotBlank(message = "El nombre del lote es obligatorio")
    private String nombreLote;
    
    private String descripcion;
    
    @NotNull(message = "El proveedor es obligatorio")
    private Long idProveedor;
    private BigDecimal precio;
    private String url;

}
