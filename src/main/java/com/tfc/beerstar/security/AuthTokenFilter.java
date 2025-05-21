package com.tfc.beerstar.security;

import java.io.IOException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tfc.beerstar.service.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Filtro de servlet que se ejecuta una vez por petición para procesar y validar
 * JWT (JSON Web Token) enviado en la cabecera HTTP Authorization.
 *
 * <p>
 * Este filtro extiende {@link OncePerRequestFilter} de Spring Framework y
 * realiza:
 * <ol>
 * <li>Extracción del token JWT del encabezado Authorization.</li>
 * <li>Validación de la firma y tiempos del token usando {@link JwtUtils}.</li>
 * <li>Recuperación del usuario (email) dentro del token.</li>
 * <li>Carga de los detalles del usuario desde la base de datos mediante
 * {@link UserDetailsServiceImpl}.</li>
 * <li>Construcción de un objeto
 * {@link org.springframework.security.authentication.UsernamePasswordAuthenticationToken}
 * con las autoridades del usuario.</li>
 * <li>Establecimiento de la autenticación en el contexto de seguridad de
 * Spring.</li>
 * </ol>
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
     * Servicio para cargar datos de usuario (credenciales y autoridades) desde
     * la BBDD.
     */
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Logger para registrar eventos de validación de JWT.
     */
    private static final Logger logs = LoggerFactory.getLogger(AuthTokenFilter.class);

    /**
     * Método que procesa cada petición HTTP una sola vez.
     *
     * @param request Petición HTTP entrante
     * @param response Respuesta HTTP saliente
     * @param filterChain Cadena de filtros para continuar el procesamiento
     * @throws ServletException En caso de error en el filtro
     * @throws IOException En caso de error de E/S
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        // 1) Logea método y URI para identificar la petición
        logs.debug(">>> Petición entrante: {} {}", request.getMethod(), request.getRequestURI());

        // 2) Logea todos los headers para diagnosticar
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            logs.debug("    Header: {} = {}", name, request.getHeader(name));
        }

        // 3) Ahora extrae y logea el Authorization raw
        String headerAuth = request.getHeader("Authorization");
        logs.debug(">>> Authorization header raw: [{}]", headerAuth);
        try {
            // Extrae el token JWT del encabezado Authorization
            String jwt = parseJwt(request);
            logs.debug(">>> Token extraído: [{}]", jwt);
            // Si el token no es nulo y es válido, establece la autenticación en el contexto de seguridad
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                // Obtiene el nombre de usuario (email) del token
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                // Carga los detalles del usuario desde la base de datos
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Crea un objeto Authentication para Spring Security
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                // Adjunta detalles de la petición (IP, sesión, cabeceras)                
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establece la autenticación en el contexto de seguridad de Spring
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException ex) {
            // Captura cualquier excepción durante el procesamiento del token JWT
            logs.error("Error al procesar JWT en AuthTokenFilter: {}", ex.getMessage());
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
            return headerAuth.substring(7).trim();
        }

        return null;
    }
}
