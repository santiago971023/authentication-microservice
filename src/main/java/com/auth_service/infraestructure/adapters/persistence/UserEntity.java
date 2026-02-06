package com.auth_service.infraestructure.adapters.persistence;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Table("users")
public class UserEntity {

    @Id
    private Long id; // Spring R2DBC manejará el autoincremental si es null al guardar
    private String dni;
    private String name;
    private String lastname;
    private LocalDate birthday;
    private BigDecimal baseSalary;
    private String email;
    private String password;
    private String role;

}
