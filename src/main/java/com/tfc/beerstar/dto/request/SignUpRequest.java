package com.tfc.beerstar.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SignUpRequest {
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    @NotBlank
    private String tipoUsuario;
    
    private String rol = "USER"; // Por defecto
    
    // Campos específicos para Cliente
    private String nombreCliente;
    private String apellidosCliente;
    private String telefonoCliente;
    private String direccionCliente;
    
    // Campos específicos para Proveedor
    private String nombreEmpresa;
    private String cifEmpresa;
    private String telefonoEmpresa;
    private String direccionEmpresa;
}