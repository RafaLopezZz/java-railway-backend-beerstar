package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    private Long idUsuario;
    @NotBlank
    private String nombre;
    private String direccion;
    private String telefono;
}
