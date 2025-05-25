package com.tfc.beerstar.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * DTO de petición que representa los datos necesarios para crear o actualizar
 * un proveedor.
 *
 * <p>
 * Este objeto se puede usar directamente o como parte del proceso de creación
 * de un {@code Usuario} con tipo "PROVEEDOR", normalmente anidado dentro de
 * {@link UsuarioRequestDTO}.</p>
 *
 * <p>
 * Contiene los datos básicos del proveedor, como su nombre comercial, datos de
 * contacto y enlace web.</p>
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
@Schema(
        name = "ProveedorRequest",
        description = "Datos requeridos para crear o actualizar la información de un proveedor"
)
public class ProveedorRequestDTO {

    @Schema(
            description = "ID único del usuario asociado al proveedor",
            example = "7",
            required = false
    )
    private Long idUsuario;

    @Schema(
            description = "Nombre comercial del proveedor",
            example = "Cervezas Artesanales S.A.",
            required = true
    )
    private String nombre;

    @Schema(
            description = "Dirección física del proveedor",
            example = "Calle Tal 69",
            required = true
    )
    private String direccion;

    @Schema(
            description = "Número de teléfono de contacto del proveedor",
            example = "666223344",
            required = true
    )
    private String telefono;

    @Schema(
            description = "URL del sitio web del proveedor",
            example = "https://cervezasartesanales.com",
            required = false
    )
    private String url;
}
