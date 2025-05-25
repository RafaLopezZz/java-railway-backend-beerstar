package com.tfc.beerstar.dto.response;


import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class OrdenVentaProveedorResponseDTO {
    private Long idOvp;
    private String numeroOrden;
    private Instant fechaCreacion;
    private String estado;
    private BigDecimal total;
    private String observaciones;
    private ProveedorResponseDTO proveedor;
    private List<DetalleOvpResponseDTO> detalles;
}
