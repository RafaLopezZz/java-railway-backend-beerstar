package com.rlp.ecommerce.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

/**
 * DTO de respuesta que representa los datos de un lote.
 *
 * <p>Este objeto es utilizado para devolver al cliente (frontend) la información
 * relevante sobre un lote, incluyendo los detalles del proveedor y las propiedades
 * asociadas al lote, como su nombre, descripción, precio y URL.</p>
 *
 * <p>Contiene también información del proveedor asociado al lote, como su ID
 * y nombre, lo que permite al cliente identificar fácilmente el proveedor del lote.</p>
 *
 * Ejemplo de respuesta:
 * <pre>
 * {
 *   "idLote": 3,
 *   "nombreLote": "Lote Primavera 2025",
 *   "descripcion": "Lote de cervezas de edición limitada para la primavera del 2025",
 *   "idProveedor": 5,
 *   "nombreProveedor": "Cervezas Artesanales S.A.",
 *   "precio": 25.99,
 *   "url": "https://cervezasartesanales.com/lote-primavera-2025"
 * }
 * </pre>
 * 
 * @author rafalopezzz
 */
@Hidden
@Data
public class LoteResponseDTO {

    private Long idLote;
    private String nombreLote;
    private String descripcion;
    private Long idProveedor;
    private String nombreProveedor;
    private Integer stock;
    private BigDecimal precio;
    private String url;
}
