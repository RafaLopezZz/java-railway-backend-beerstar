package com.tfc.beerstar.dto.request;

import lombok.Data;

/**
 * DTO de petición para registrar o actualizar los datos de un cliente.
 * 
 * <p>Este objeto se utiliza como parte de la creación de un usuario con tipo "CLIENTE",
 * o para actualizar los datos personales del cliente una vez creado.</p>
 * 
 * <p>Puede estar anidado dentro de {@link UsuarioRequestDTO} si se envía la creación
 * de usuario con los datos del cliente.</p>
 *
 * Ejemplo de uso:
 * <pre>
 * {
 *   "idUsuario": 1,
 *   "nombre": "Rafael López",
 *   "direccion": "Calle Tal 69",
 *   "telefono": "666223344"
 * }
 * </pre>
 * 
 * @author rafalopezzz
 */
@Data
public class ClienteRequestDTO {

    private Long idUsuario;
    private String nombre;
    private String direccion;
    private String telefono;

}
