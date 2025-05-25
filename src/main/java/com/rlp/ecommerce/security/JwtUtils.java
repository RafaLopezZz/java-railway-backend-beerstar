package com.rlp.ecommerce.security;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

/**
 * Componente de Spring para gestión de tokens JWT.
 */
@Slf4j
@Component
public class JwtUtils {

    /**
     * Secreto para firmar el JWT. Debe estar codificado en Base64.
     */
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /**
     * Tiempo de validez del token en milisegundos.
     */
    @Value("${app.jwt.expirationMs}")
    private int jwtExpirationMs;

    /**
     * Genera un token JWT firmado para el user autenticado.
     *
     * @param authentication Objeto de Spring Security con la información del
     * usuario autenticado
     * @return Token JWT firmado (compact form)
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        // Extraer roles del usuario autenticado
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Añadir el tipo de usuario como un rol adicional
        // Asumiendo que tienes un método getTipoUsuario() en UserDetailsImpl
        if (userDetails.getTipoUsuario() != null) {
            // Añadir el prefijo ROLE_ si estás usando hasRole() en tu configuración de seguridad
            roles.add("ROLE_" + userDetails.getTipoUsuario());
        }

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // email u otro identificador
                .claim("roles", roles) // añadir roles como claim
                .setIssuedAt(now) // fecha de emisión
                .setExpiration(expiryDate) // fecha de expiración
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();                                   // genera la cadena final
    }

    /**
     * Obtiene los roles del usuario desde el token JWT
     */
    public List<String> getRolesFromJwtToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> roles = new ArrayList<>();

        if (claims.get("roles") != null) {
            if (claims.get("roles") instanceof List) {
                List<?> rawList = (List<?>) claims.get("roles");
                roles = rawList.stream()
                        .filter(item -> item instanceof String)
                        .map(item -> (String) item)
                        .collect(Collectors.toList());
            }
        }
        return roles;
    }

    /**
     * Obtiene el nombre de usuario (subject) desde un token JWT.
     *
     * @param token Token JWT en forma de cadena
     * @return Subject (por ejemplo, email) almacenado en el token
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida la integridad y validez temporal de un token JWT.
     *
     * @param authToken Token JWT a validar
     * @return {@code true} si el token es válido y no ha expirado
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Token JWT inválido: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Token JWT expirado: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Token JWT no soportado: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("Token JWT vacío o nulo: {}", ex.getMessage());
        }
        return false;
    }

    /**
     * Construye la clave secreta (Key) a partir de la cadena Base64
     * configurada.
     *
     * @return Instancia de {@link Key} para firmar/verificar JWT
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
