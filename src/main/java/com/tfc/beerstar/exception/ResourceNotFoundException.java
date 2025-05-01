package com.tfc.beerstar.exception;

/**
 * Excepción para cuando no se encuentra un recurso (por ejemplo, un usuario).
 * Se utiliza para devolver un error 404.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
