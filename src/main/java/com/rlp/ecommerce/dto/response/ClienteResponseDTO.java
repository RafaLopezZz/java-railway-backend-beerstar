package com.rlp.ecommerce.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * DTO de respuesta que representa los datos completos de un cliente.
 *
 * <p>Este objeto se utiliza para devolver al cliente (frontend) la información
 * relevante de un cliente registrado, incluyendo su relación con el usuario
 * y su fecha de registro.</p>
 *
 * <p>Se puede usar, por ejemplo, al consultar el perfil de un cliente o al listar
 * clientes desde el backend. Incorpora un {@link UsuarioResponseDTO} para representar
 * la información del usuario asociado sin exponer datos sensibles.</p>
 *
 * Ejemplo de respuesta:
 * <pre>
 * {
 *   "idCliente": 12,
 *   "usuario": {
 *     "idUsuario": 5,
 *     "email": "cliente@ejemplo.com",
 *     "rol": "USER",
 *     "tipoUsuario": "CLIENTE"
 *   },
 *   "nombre": "Rafael López",
 *   "direccion": "Calle Tal 69",
 *   "telefono": "666223344",
 *   "fechaRegistro": "2024-03-10T14:30:00"
 * }
 * </pre>
 * 
 * @author rafalopezzz
 */
@Data
public class ClienteResponseDTO {

    private Long idCliente;
    private UsuarioResponseDTO usuario;
    private String nombre;
    private String direccion;
    private String telefono;
    private LocalDateTime fechaRegistro;
}
