package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticulosRequestDTO {

    @NotBlank
    private String nombre;

    private String descripcion;

    @DecimalMin(value = "0.0")
    private Double precio;
    
    @Min(0)
    private Integer stock;
    
    private Long idCategoria;
    
    private Double graduacion;
    
    private String url;

}
