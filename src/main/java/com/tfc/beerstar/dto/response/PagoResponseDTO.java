package com.tfc.beerstar.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.Data;

@Data
public class PagoResponseDTO {
    private Long id;
    private Long idPedido;
    private BigDecimal monto;
    private String estadoPago;
    private Instant fechaPago;
    private String metodoPago;

}
