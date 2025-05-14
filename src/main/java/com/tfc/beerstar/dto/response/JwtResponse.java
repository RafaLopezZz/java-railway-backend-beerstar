package com.tfc.beerstar.dto.response;

import java.util.List;

import lombok.Data;

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
