package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddToCarritoRequestDTO {

    private Long idArticulo;

    @Min(1)
    private Integer cantidad;

}
