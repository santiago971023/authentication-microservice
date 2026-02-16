package com.auth_service.infraestructure.shared;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestValidator {

    private final Validator validator;

    public <T> Mono<T> validate(T target) {
        Set<ConstraintViolation<T>> violations = validator.validate(target);

        if (violations.isEmpty()) {
            return Mono.just(target);
        }

        // AQUÍ ESTÁ EL TRUCO: Lanzamos ConstraintViolationException envuelta en Mono.error
        return Mono.error(new ConstraintViolationException(violations));
    }
}
