package com.rlp.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class PedidoResponseDTO {

    private Long idPedido;
    private Long idUsuario;
    private Long IdCliente;
    private String nombreCliente;
    private String emailUsuario;
    private String direccionCliente;
    private Instant fechaPedido;
    private String estadoPedido;
    private BigDecimal subTotal;
    private BigDecimal iva;
    private BigDecimal gastosEnvio;
    private BigDecimal total;
    private String metodoPago;
    private String idTransaccion;
    private List<DetallePedidoResponseDTO> detalles;

    public List<DetallePedidoResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoResponseDTO> detalles) {
        this.detalles = detalles;
    }

}
