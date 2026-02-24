package com.auth_service.infraestructure.entryPoints.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email(message = "El formato del 'email' no es válido.")
        @NotBlank(message = "El campo email no puede estar vacío")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 carácteres.")
        String password

) {
}
