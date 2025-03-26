package com.tfc.beerstar.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ClienteResponseDTO {

    private Long idCliente;
    private UsuarioResponseDTO usuario;
    private String nombre;
    private String direccion;
    private String telefono;
    private LocalDateTime fechaRegistro;
}
