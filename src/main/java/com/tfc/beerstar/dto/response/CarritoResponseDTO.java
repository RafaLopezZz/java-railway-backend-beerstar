package com.tfc.beerstar.dto.response;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class CarritoResponseDTO {

    private Long id;
    private Instant fechaCreacion;
    private List<DetalleCarritoResponseDTO> items;

}
