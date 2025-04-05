package com.tfc.beerstar.dto.response;

import lombok.Data;

@Data
public class ArticulosResponseDTO {

    private Long idArticulo;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Long idCategoria;
    private String nombreCategoria;
    private Double graduacion;
    private String imagen;

}
