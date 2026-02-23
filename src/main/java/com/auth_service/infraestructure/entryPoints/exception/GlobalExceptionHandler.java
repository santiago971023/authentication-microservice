package com.auth_service.infraestructure.entryPoints.exception;

import com.auth_service.domain.exception.UserAlreadyExistsException;
import com.auth_service.infraestructure.entryPoints.dto.ErrorDetailDto;
import com.auth_service.infraestructure.entryPoints.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;
import jakarta.validation.ConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejar los errores de validación:
    @ExceptionHandler(ConstraintViolationException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleConstraintViolation(ConstraintViolationException ex){

        // Convertimos los errores de Spring a la lista personalizada ErrorDetailDto
        List<ErrorDetailDto> errorDetails = ex.getConstraintViolations().stream()
                .map(violation -> {
                    String rawPath = violation.getPropertyPath().toString();
                    String fieldName = rawPath.contains(".")
                            ? rawPath.substring(rawPath.lastIndexOf('.') + 1)
                            : rawPath;

                    return ErrorDetailDto.builder()
                            .field(fieldName)
                            .description(violation.getMessage())
                            .build();
                })
                .toList();

        return buildErrorResponse("INVALID_DATA", "Datos inválidos", errorDetails, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public Mono<ResponseEntity<ErrorResponseDto>> handleUserExists(UserAlreadyExistsException ex){
        return buildErrorResponse("USER_ALREADY_EXISTS", ex.getMessage(), null, HttpStatus.CONFLICT);
    }

    // Este método evita que repitamos el código de .builder() y ResponseEntity una y otra vez.
    private Mono<ResponseEntity<ErrorResponseDto>> buildErrorResponse(
            String code,
            String message,
            List<ErrorDetailDto> details,
            HttpStatus status) {

        ErrorResponseDto response = ErrorResponseDto.builder()
                .code(code)
                .message(message)
                .errors(details)
                .build();

        return Mono.just(ResponseEntity.status(status).body(response));
    }





}
