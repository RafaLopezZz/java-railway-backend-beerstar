package com.tfc.beerstar.exception;

/**
 * Excepción personalizada que se lanza cuando un usuario proporciona
 * una contraseña inválida durante un intento de autenticación.
 *
 * <p>Esta excepción extiende {@link RuntimeException}, por lo que no es
 * necesario declararla o capturarla explícitamente.</p>
 *
 * <p>Suele usarse en el contexto de login o validaciones internas de seguridad,
 * y típicamente se maneja devolviendo un error HTTP 401 (Unauthorized) al cliente.</p>
 *
 * @author rafalopezzz
 */

public class InvalidPasswordException extends RuntimeException {
    /**
     * Crea una nueva instancia de {@code InvalidPasswordException} con un mensaje personalizado.
     *
     * @param message Mensaje que describe el motivo del error.
     */
    public InvalidPasswordException(String message) {
        super(message);
    }
}