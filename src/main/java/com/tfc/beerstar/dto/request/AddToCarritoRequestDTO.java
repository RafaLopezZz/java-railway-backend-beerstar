package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCarritoRequestDTO {

    @NotNull
    private Long articuloId;
    @Min(1)
    private Integer cantidad;

}
