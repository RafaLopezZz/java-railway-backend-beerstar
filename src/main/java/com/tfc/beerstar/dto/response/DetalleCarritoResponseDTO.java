package com.tfc.beerstar.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleCarritoResponseDTO {

    private Long id;
    private Long articuloId;
    private String nombreArticulo;
    private Integer cantidad;
    private BigDecimal precioUnitario;

}
