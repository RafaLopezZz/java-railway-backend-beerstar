package com.rlp.ecommerce.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DetallePedidoResponseDTO {

    private Long idPedido;
    private Long idArticulo;
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
