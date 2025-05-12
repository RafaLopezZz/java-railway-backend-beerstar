package com.tfc.beerstar.security;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtro de servlet que se ejecuta una vez por petición para procesar y validar
 * JWT (JSON Web Token) enviado en la cabecera HTTP Authorization.
 */
@Slf4j
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    /**
     * Instancia de utilidades JWT para firma y validación de tokens.
     */
    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Método que procesa cada petición HTTP una sola vez.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extrae el token JWT del encabezado Authorization
            String jwt = parseJwt(request);
            // Si el token no es nulo y es válido, establece la autenticación en el contexto de seguridad
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Obtiene el nombre de usuario (email) del token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Obtener los roles directamente del token JWT
                List<String> roles = jwtUtils.getRolesFromJwtToken(jwt);

                // Convertir los roles a GrantedAuthority
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                // Crear un objeto Authentication para Spring Security
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                                username, // Principal (nombre de usuario)
                                null, // Credenciales (null porque no necesitamos la contraseña)
                                authorities); // Autoridades/roles

                // Adjunta detalles de la petición (IP, sesión, cabeceras)                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // Captura cualquier excepción durante el procesamiento del token JWT
            log.error("No se pudo establecer la autenticación del usuario: {}", e.getMessage());
            // No propagamos la excepción para permitir que requests sin token sigan el flujo normal
        }
        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);
    }

    /**
     * Extrae el token JWT de la cabecera Authorization si existe y comienza con
     * "Bearer ".
     *
     * @param request Petición HTTP
     * @return Cadena del token sin el prefijo, o {@code null} si no se
     * encuentra
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
