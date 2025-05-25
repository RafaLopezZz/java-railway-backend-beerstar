package com.rlp.ecommerce.dto.response;

import java.util.List;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;

/**
 * DTO de respuesta para la autenticación JWT.
 * 
 * <p>Contiene el token JWT, tipo de token, ID del usuario, email, tipo de usuario y roles asociados.</p>
 */
@Hidden
@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long idUsuario;
    private String email;
    private String tipoUsuario;
    private List<String> roles;

    public JwtResponse(String token, Long idUsuario, String email, String tipoUsuario, List<String> roles) {
        this.token = token;
        this.idUsuario= idUsuario;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.roles = roles;
    }
}
