package com.rlp.ecommerce.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(
        name = "LoginRequest",
        description = "Credenciales de usuario para autenticación"
)
public class LoginRequest {

    @Schema(
            description = "Dirección de correo electrónico del usuario",
            example = "usuario@ejemplo.com",
            required = true
    )
    @NotBlank
    private String email;

    @Schema(
            description = "Contraseña del usuario",
            example = "miPassword123",
            required = true
    )
    @NotBlank
    private String password;
}
