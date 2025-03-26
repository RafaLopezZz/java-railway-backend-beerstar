package com.tfc.beerstar.exception;

/**
 * Excepción que se lanza cuando la contraseña no es válida
 * Se utiliza para devolver un error 401
 */
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }

}
