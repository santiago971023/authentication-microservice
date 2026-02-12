package com.auth_service.infraestructure.dto;

import java.math.BigDecimal;

public record UserResponseDto(
         Long id,
         String name,
         String lastname,
         BigDecimal baseSalary,
         String email
) {
}
