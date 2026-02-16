package com.auth_service.infraestructure.entryPoints.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetailDto {

    private String field;

    private String description;

}
