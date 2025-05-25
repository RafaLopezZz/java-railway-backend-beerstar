package com.rlp.ecommerce.exception;

/**
 * Excepción personalizada que se lanza cuando un recurso solicitado
 * no existe o no puede ser encontrado en el sistema.
 *
 * <p>Esta excepción suele utilizarse en servicios donde se buscan entidades
 * por ID u otros atributos únicos, y no se encuentra una coincidencia.</p>
 *
 * @author rafalopezzz
 */

public class ResourceNotFoundException extends RuntimeException {
    /**
     * Crea una nueva instancia de {@code ResourceNotFoundException}
     * con el mensaje proporcionado.
     *
     * @param message Mensaje descriptivo sobre el recurso no encontrado.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}