package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    private Long idUsuario;

    @NotBlank
    private String nombre;
    
    private String direccion;
    private String telefono;

}
