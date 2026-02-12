package com.auth_service.infraestructure.shared;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class RequestValidator {

    private final Validator validator; // Jakarta Bean Validation (Hibernate Validator)

    public RequestValidator(Validator validator) {
        this.validator = validator;
    }

    public <T> Mono<T> validate(T body) {
        Set<ConstraintViolation<T>> violations = validator.validate(body);

        if (!violations.isEmpty()) {
            BeanPropertyBindingResult bindingResult =
                new BeanPropertyBindingResult(body, body.getClass().getSimpleName());

            for (ConstraintViolation<T> violation : violations) {
                String field = violation.getPropertyPath().toString();
                String message = violation.getMessage();
                bindingResult.addError(new FieldError(
                    bindingResult.getObjectName(),
                    field,
                    message
                ));
            }

            return Mono.error(new ServerWebInputException(bindingResult.toString()));
        }

        return Mono.just(body);
    }
}
