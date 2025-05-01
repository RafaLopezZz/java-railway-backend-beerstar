package com.tfc.beerstar.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProveedorResponseDTO {

    private Long idProveedor;
    private UsuarioResponseDTO usuario;
    private String nombre;
    private String direccion;
    private String telefono;
    private LocalDateTime fechaRegistro;
}
