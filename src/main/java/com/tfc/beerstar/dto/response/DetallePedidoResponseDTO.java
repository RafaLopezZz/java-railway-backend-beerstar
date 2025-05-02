package com.tfc.beerstar.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetallePedidoResponseDTO {

    private Long id;
    private Long articuloId;
    private String nombreArticulo;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal totalLinea;


    public BigDecimal getTotalLinea() {
        return totalLinea;
    }

    public void setTotalLinea(BigDecimal totalLinea) {
        this.totalLinea = totalLinea;
    }
}
