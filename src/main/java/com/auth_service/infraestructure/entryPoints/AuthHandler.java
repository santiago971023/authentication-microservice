package com.auth_service.infraestructure.entryPoints;

import com.auth_service.application.ports.in.LoginInputPort;
import com.auth_service.infraestructure.entryPoints.dto.LoginRequest;
import com.auth_service.infraestructure.entryPoints.dto.TokenResponse;
import com.auth_service.infraestructure.mapper.LoginRestMapper;
import com.auth_service.infraestructure.shared.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthHandler {

    private final RequestValidator validator;
    private final LoginRestMapper loginRestMapper;
    private final LoginInputPort loginInputPort;

    public AuthHandler(RequestValidator validator, LoginRestMapper loginRestMapper, LoginInputPort loginInputPort) {
        this.validator = validator;
        this.loginRestMapper = loginRestMapper;
        this.loginInputPort = loginInputPort;
    }

    Mono<ServerResponse> login(ServerRequest request){
        log.info("== Petición recibida para Login ==");
        return request.bodyToMono(LoginRequest.class)
                .doOnNext(dto -> log.debug("Dto extraído del cuerpo de la petición {}", dto))
                .flatMap(validator::validate)
                .map(loginRestMapper::toInput)
                .flatMap(loginInputPort::login)
                .flatMap(loginAproved -> {
                    TokenResponse tokenResponse = new TokenResponse(loginAproved);
                    log.info("¡Login Existoso!");
                    return ServerResponse.status(HttpStatus.OK).bodyValue(tokenResponse);
                });
    }

}
