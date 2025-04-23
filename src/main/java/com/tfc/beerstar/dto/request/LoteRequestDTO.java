package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoteRequestDTO {

    @NotBlank
    private Long idLote;
    private String nombreLote;
    private String descripcion;
    private Long idProveedor;
    private Double precio;
    private String url;

}
