package com.tfc.beerstar.dto.response;

import lombok.Data;

@Data
public class LoteResponseDTO {

    private Long idLote;
    private String nombreLote;
    private String descripcion;
    private Long idProveedor;
    private String nombreProveedor;
    private Double precio;
    private String url;
    private String fechaValidez;
}
