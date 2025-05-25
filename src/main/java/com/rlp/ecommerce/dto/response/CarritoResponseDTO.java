package com.rlp.ecommerce.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class CarritoResponseDTO {

    private Long id;
    private Instant fechaCreacion;
    private List<DetalleCarritoResponseDTO> items;
    private BigDecimal subtotal;
    private BigDecimal impuestos;
    private BigDecimal gastosEnvio;
    private BigDecimal total;

}
