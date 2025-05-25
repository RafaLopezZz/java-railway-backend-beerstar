package com.rlp.ecommerce.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetalleOvpResponseDTO {

    private Long idDetalleOvp;
    private ArticulosResponseDTO articulo;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

}
