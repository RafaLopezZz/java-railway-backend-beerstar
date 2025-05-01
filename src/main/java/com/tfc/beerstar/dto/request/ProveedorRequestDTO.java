package com.tfc.beerstar.dto.request;

import lombok.Data;

/**
 * DTO de petición que representa los datos necesarios para crear o actualizar un proveedor.
 *
 * <p>Este objeto se puede usar directamente o como parte del proceso de creación
 * de un {@code Usuario} con tipo "PROVEEDOR", normalmente anidado dentro de {@link UsuarioRequestDTO}.</p>
 *
 * <p>Contiene los datos básicos del proveedor, como su nombre comercial, datos de contacto y enlace web.</p>
 *
 * Ejemplo de uso en una petición JSON:
 * <pre>
 * {
 *   "idUsuario": 7,
 *   "nombre": "Cervezas Artesanales S.A.",
 *   "direccion": "Calle Tal 69",
 *   "telefono": "666223344",
 *   "url": "https://cervezasartesanales.com"
 * }
 * </pre>
 * 
 * @author rafalopezzz
 */
@Data
public class ProveedorRequestDTO {

    private Long idUsuario;
    private String nombre;
    private String direccion;
    private String telefono;
    private String url;
}
