package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriasRequestDTO {

    @NotBlank
    private String nombre;
    private String descripcion;

}
