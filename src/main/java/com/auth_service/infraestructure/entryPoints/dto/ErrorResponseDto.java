package com.auth_service.infraestructure.entryPoints.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponseDto {

    private List<ErrorDetailDto> errors;

    private String message;

    private String code;

}
