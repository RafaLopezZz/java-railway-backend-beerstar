package com.tfc.beerstar.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PagoRequestDTO {

    @NotNull 
    private Long pedidoId;
    
    @NotNull 
    @DecimalMin("0.0") 
    private BigDecimal monto;
    
    @NotBlank 
    private String metodoPago;
}
