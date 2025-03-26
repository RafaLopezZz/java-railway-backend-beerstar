package com.tfc.beerstar.dto.response;

import lombok.Data;

@Data
public class UsuarioResponseDTO {

    private Long idUsuario;
    private String email;
    private String rol;
    private String tipoUsuario;

}
