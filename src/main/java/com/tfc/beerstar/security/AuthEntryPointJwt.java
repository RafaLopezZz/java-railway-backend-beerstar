package com.tfc.beerstar.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Punto de entrada para manejar accesos no autorizados en la aplicación.
 *
 * <p>Implementa {@link org.springframework.security.web.AuthenticationEntryPoint} para
 * interceptar las peticiones que no estén autenticadas o no tengan permisos suficientes,
 * enviando una respuesta HTTP 401 (Unauthorized).</p>
 *
 * <p>Este componente se registra con {@link Component} y usa SLF4J para registrar el motivo
 * del rechazo.</p>
 *
 * @see org.springframework.security.web.AuthenticationEntryPoint
 * @author rafalopezzz
 */
@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logs = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    /**
     * Maneja las peticiones no autorizadas.
     *
     * <p>Este método se invoca cuando ocurre una excepción de autenticación
     * antes de llegar al recurso protegido. Registra un error en el log y envía
     * un código 401 con un mensaje genérico.</p>
     *
     * @param request       Petición HTTP que provocó el fallo de autenticación
     * @param response      Respuesta HTTP donde escribir el error
     * @param authException Excepción que describe el motivo del fallo
     * @throws IOException      En caso de error de entrada/salida al escribir la respuesta
     * @throws ServletException Nunca se lanza directamente
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logs.error("Error no autorizado: {}", authException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: No autorizado");
    }
}
