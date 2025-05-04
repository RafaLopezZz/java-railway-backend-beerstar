package com.tfc.beerstar.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String tipoUsuario;
    private List<String> roles;

    public JwtResponse(String token, Long id, String email, String tipoUsuario, List<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
        this.roles = roles;
    }
}
