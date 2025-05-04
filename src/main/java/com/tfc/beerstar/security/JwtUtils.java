package com.tfc.beerstar.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

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
     * @param authentication Objeto de Spring Security con la información del usuario autenticado
     * @return Token JWT firmado (compact form)
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())        // email u otro identificador
                .setIssuedAt(now)                             // fecha de emisión
                .setExpiration(expiryDate)                    // fecha de expiración
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();                                   // genera la cadena final
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
     * Construye la clave secreta (Key) a partir de la cadena Base64 configurada.
     *
     * @return Instancia de {@link Key} para firmar/verificar JWT
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
