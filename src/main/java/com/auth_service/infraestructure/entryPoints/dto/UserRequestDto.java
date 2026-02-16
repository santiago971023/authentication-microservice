package com.auth_service.infraestructure.entryPoints.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserRequestDto(
        @NotBlank(message = "El DNI es obligatorio.")
         String dni,

        @NotBlank(message = "El campo 'name' no puede ser nulo o vacío.")
        @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s\\p{Punct}]+$", message = "El campo 'name' solo debe contener letras y espacios.")
         String name,

        @NotBlank(message = "El campo 'lastname' no puede ser nulo o vacío.")
        @Pattern(regexp = "^[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s\\p{Punct}]+$", message = "El campo 'name' solo debe contener letras y espacios.")
         String lastname,


         @NotNull(message = "El campo 'birthday' no puede ser nulo o vacío.")
         @Past(message = "La fecha de nacimiento debe estar en el pasado.")
         LocalDate birthday,

        //@Schema(description = "Salario del usuario", example = "1500000")
        @NotNull(message = "El salario base no puede ser nulo.")
        @DecimalMin(value = "0.0", inclusive = false, message = "El salario base debe ser mayor que cero.")
        @DecimalMax(value = "15000000.0", message = "El salario base no puede exceder los 15,000,000.")
         BigDecimal baseSalary,

         String role,

         @Email(message = "El formato del 'email' no es válido.")
        @NotBlank(message = "El campo email no puede estar vacío")
         String email,

        @NotBlank(message = "La contraseña es obligatoria")
                @Size(min = 8, message = "La contraseña debe tener al menos 8 carácteres.")
        String password
) {


}
