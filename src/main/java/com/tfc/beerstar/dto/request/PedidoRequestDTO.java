package com.tfc.beerstar.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.tfc.beerstar.model.DetallePedido;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(
        name = "PedidoRequest",
        description = "Datos para crear o actualizar un pedido"
)
public class PedidoRequestDTO {

    @Schema(
            description = "ID único del pedido",
            example = "1",
            required = true
    )
    @NotBlank
    private Long idPedido;

    @Schema(
            description = "ID único del cliente que realiza el pedido",
            example = "1",
            required = true
    )
    @NotBlank
    private Long idCliente;

    @Schema(
            description = "ID único del usuario asociado",
            example = "1",
            required = false
    )
    private Long idUsuario;

    @Schema(
            description = "Estado actual del pedido",
            example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "CONFIRMADO", "ENVIADO", "ENTREGADO", "CANCELADO"}
    )
    private String estadoPedido;

    @Schema(
            description = "Lista de artículos incluidos en el pedido"
    )
    private List<DetallePedido> detallePedido;

    @Schema(
            description = "Subtotal del pedido sin IVA ni gastos de envío",
            example = "25.50"
    )
    private BigDecimal subTotal;

    @Schema(
            description = "Importe del IVA aplicado",
            example = "5.36"
    )
    private BigDecimal iva;

    @Schema(
            description = "Gastos de envío aplicados al pedido",
            example = "3.99"
    )
    private BigDecimal gastosEnvio;

    @Schema(
            description = "Total del pedido incluyendo IVA y gastos de envío",
            example = "34.85"
    )
    private BigDecimal total;

    @Schema(
            description = "Método de pago utilizado para el pedido",
            example = "Tarjeta de Crédito"
    )
    private String metodoPago;

    @Schema(
            description = "ID de la transacción asociada al pedido",
            example = "TX - 1234567890"
    )
    private Long idTransaccion;

}
