package com.tfc.beerstar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones para la API REST de Beerstar.
 *
 * <p>Usando la anotación {@code @RestControllerAdvice}, esta clase captura las excepciones
 * lanzadas por los controladores y las transforma en respuestas HTTP legibles para el cliente.</p>
 *
 * <p>Define manejadores específicos para excepciones personalizadas como:</p>
 * <ul>
 *   <li>{@link EmailAlreadyExistsException} → HTTP 409 Conflict</li>
 *   <li>{@link ResourceNotFoundException} → HTTP 404 Not Found</li>
 *   <li>{@link InvalidPasswordException} → HTTP 401 Unauthorized</li>
 * </ul>
 *
 * <p>Esto permite que la API devuelva respuestas consistentes y fáciles de consumir desde el frontend.</p>
 * 
 * @author rafalopezzz
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    /**
     * Maneja la excepción {@link EmailAlreadyExistsException}.
     *
     * @param ex La excepción lanzada cuando un email ya está registrado.
     * @return Una respuesta HTTP 409 Conflict con el mensaje de error.
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        // Puedes devolver un status 409 Conflict, que es adecuado para conflictos de recursos
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    /**
     * Maneja la excepción {@link ResourceNotFoundException}.
     *
     * @param ex La excepción lanzada cuando un recurso solicitado no fue encontrado.
     * @return Una respuesta HTTP 404 Not Found con el mensaje de error.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Maneja la excepción {@link InvalidPasswordException}.
     *
     * @param ex La excepción lanzada cuando una contraseña es incorrecta o inválida.
     * @return Una respuesta HTTP 401 Unauthorized con el mensaje de error.
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}