package com.rlp.ecommerce.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * DTO de respuesta que representa los datos completos de un proveedor.
 *
 * <p>Este objeto es utilizado para devolver al cliente (frontend) la información
 * relevante sobre un proveedor, incluyendo su relación con el usuario y detalles
 * como la fecha de registro y la URL asociada.</p>
 *
 * <p>Contiene un {@link UsuarioResponseDTO} para representar la información del
 * usuario asociado al proveedor sin exponer datos sensibles, como la contraseña.</p>
 *
 * Ejemplo de respuesta en formato JSON:
 * <pre>
 * {
 *   "idProveedor": 3,
 *   "usuario": {
 *     "idUsuario": 8,
 *     "email": "cervezas@artesanales.com",
 *     "rol": "USER",
 *     "tipoUsuario": "PROVEEDOR"
 *   },
 *   "nombre": "Cervezas Artesanales S.A.",
 *   "direccion": "Calle Tal 69",
 *   "telefono": "666223344",
 *   "fechaRegistro": "2024-05-01T09:00:00",
 *   "url": "https://cervezasartesanales.com"
 * }
 * </pre>
 * 
 * @author rafalopezzz
 */
@Data
public class ProveedorResponseDTO {

    private Long idProveedor;
    private UsuarioResponseDTO usuario;
    private String nombre;
    private String direccion;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private String url;
}
