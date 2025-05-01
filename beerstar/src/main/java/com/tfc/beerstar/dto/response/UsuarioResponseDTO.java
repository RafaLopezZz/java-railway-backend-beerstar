package com.tfc.beerstar.dto.response;

import lombok.Data;

/**
 * DTO de respuesta para representar los datos públicos de un usuario.
 *
 * <p>Este objeto es devuelto por el backend cuando se consulta información de
 * un usuario, por ejemplo después del registro, login o en el perfil. Contiene
 * datos básicos sin exponer información sensible como la contraseña.</p>
 *
 * <p>Se utiliza comúnmente en respuestas HTTP para mantener la separación entre
 * el modelo de entidad y lo que se expone al cliente.</p>
 * 
 * Ejemplo de respuesta:
 * <pre>
 * {
 *   "idUsuario": 1,
 *   "email": "usuario@ejemplo.com",
 *   "rol": "USER",
 *   "tipoUsuario": "CLIENTE"
 * }
 * </pre>
 * 
 * @author rafalopezzz
 */
@Data
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String email;
    private String rol;
    private String tipoUsuario;

}
