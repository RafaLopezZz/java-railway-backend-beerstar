package com.rlp.ecommerce.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleCarritoResponseDTO {

    private Long id;
    private Long idArticulo;
    private String nombreArticulo;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal totalLinea;
}
