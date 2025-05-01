package com.tfc.beerstar.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta registrar un usuario
 * con un correo electrónico que ya está en uso en el sistema.
 *
 * <p>Esta excepción extiende {@code RuntimeException}, lo que permite lanzarla
 * sin necesidad de ser capturada explícitamente.</p>
 *
 * <p>Puede ser utilizada en los servicios o controladores para validar el
 * registro de usuarios y enviar una respuesta adecuada al cliente.</p>
 *
 * Ejemplo de uso:
 * <pre>
 * if (usuarioRepository.existsByEmail(email)) {
 *     throw new EmailAlreadyExistsException("El correo ya está registrado");
 * }
 * </pre>
 *
 * @author rafalopezzz
 */

public class EmailAlreadyExistsException extends RuntimeException {
    /**
     * Constructor que permite especificar un mensaje personalizado
     * para describir la causa de la excepción.
     *
     * @param message Mensaje detallado del error.
     */
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}