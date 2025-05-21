package com.tfc.beerstar.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.tfc.beerstar.model.DetallePedido;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoRequestDTO {

    @NotBlank
    private Long idPedido;

    @NotBlank
    private Long idCliente;

    private String estadoPedido;
    private List<DetallePedido> detallePedido;
    
    private BigDecimal subTotal;
    private BigDecimal iva;
    private BigDecimal gastosEnvio;
    private BigDecimal total;

    private String metodoPago;
    private Long idTransaccion;

}
