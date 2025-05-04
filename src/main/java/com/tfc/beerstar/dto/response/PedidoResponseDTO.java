package com.tfc.beerstar.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class PedidoResponseDTO {

    private Long id;
    private Long clienteId;
    private Instant fechaPedido;
    private String estado;
    private BigDecimal total;
    private List<DetallePedidoResponseDTO> detalles;

    public List<DetallePedidoResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoResponseDTO> detalles) {
        this.detalles = detalles;
    }

}
