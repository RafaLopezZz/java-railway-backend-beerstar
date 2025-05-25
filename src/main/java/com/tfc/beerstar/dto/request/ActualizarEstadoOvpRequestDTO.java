package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO de request para actualizar estado de OVP.
 */
@Data
public class ActualizarEstadoOvpRequestDTO {

    @NotNull
    private String estado;

    private String observaciones;
}
