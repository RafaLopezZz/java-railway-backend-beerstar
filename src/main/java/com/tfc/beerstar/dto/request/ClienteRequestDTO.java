package com.tfc.beerstar.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO de petición para registrar o actualizar los datos de un cliente.
 *
 * <p>
 * Este objeto se utiliza como parte de la creación de un usuario con tipo
 * "CLIENTE", o para actualizar los datos personales del cliente una vez
 * creado.</p>
 *
 * <p>
 * Puede estar anidado dentro de {@link UsuarioRequestDTO} si se envía la
 * creación de usuario con los datos del cliente.</p>
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
@Schema(
        name = "ClienteRequest",
        description = "Datos requeridos para crear o actualizar la información de un cliente"
)
public class ClienteRequestDTO {

    /**
     * Identificador único del usuario asociado al cliente.
     *
     * <p>
     * Este campo establece la relación entre el cliente y su usuario
     * correspondiente. Es obligatorio para operaciones de actualización, pero
     * puede ser nulo durante la creación cuando se envía anidado en
     * UsuarioRequestDTO.</p>
     */
    @Schema(
            description = "ID único del usuario asociado al cliente",
            example = "1",
            required = false
    )
    private Long idUsuario;

    /**
     * Nombre completo del cliente.
     *
     * <p>
     * Debe incluir nombre y apellidos del cliente para identificación en
     * pedidos y comunicaciones comerciales.</p>
     */
    @Schema(
            description = "Nombre completo del cliente",
            example = "Rafael López Plana",
            minLength = 2,
            maxLength = 20
    )
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 2 y 20 caracteres")
    private String nombre;

    /**
     * Dirección de entrega del cliente.
     *
     * <p>
     * Dirección completa donde se realizarán las entregas de pedidos. Debe
     * incluir calle, número, ciudad y código postal para facilitar el proceso
     * de envío.</p>
     */
    @Schema(
            description = "Dirección completa de entrega del cliente",
            example = "Calle Mayor 123, 4º B, 28001 Madrid",
            minLength = 10,
            maxLength = 100
    )
    @Size(min = 10, max = 100, message = "La dirección debe tener entre 10 y 100 caracteres")
    private String direccion;

    /**
     * Número de teléfono de contacto del cliente.
     *
     * <p>
     * Teléfono de contacto para comunicaciones relacionadas con pedidos,
     * entregas y atención al cliente. Debe ser un número válido.</p>
     */
    @Schema(
            description = "Número de teléfono de contacto del cliente",
            example = "666223344",
            pattern = "^[0-9+\\-\\s()]{9,15}$"
    )
    @Pattern(
            regexp = "^[0-9+\\-\\s()]{9,15}$",
            message = "El teléfono debe tener un formato válido (9-15 dígitos)"
    )
    private String telefono;

}
