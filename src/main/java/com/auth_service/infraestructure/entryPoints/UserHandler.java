package com.auth_service.infraestructure.entryPoints;

import com.auth_service.application.ports.in.UserInputPort;
import com.auth_service.infraestructure.entryPoints.dto.UserRequestDto;
import com.auth_service.infraestructure.mapper.UserRestMapper;
import com.auth_service.infraestructure.shared.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
public class UserHandler {
    private final UserInputPort userInputPort;
    private final RequestValidator requestValidator;
    private final UserRestMapper userRestMapper;

    public UserHandler(UserInputPort userInputPort, RequestValidator requestValidator, UserRestMapper userRestMapper) {
        this.userInputPort = userInputPort;
        this.requestValidator = requestValidator;
        this.userRestMapper = userRestMapper;
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest){
        // 1. Convertir el body (JSON) a DTO
        return serverRequest.bodyToMono(UserRequestDto.class)
                .doOnNext(dto -> log.info("Solicitud de registro recibida para DNI: {}" , dto.dni()))
                // 2. Aquí vamos a tener que validar el DTO
                .flatMap(requestValidator::validate)

                // 3. Convertir DTO a Dominio y llamar al Caso de Uso (por medio del puerto)
                .map(userRestMapper::toDomain)
                .flatMap(userInputPort::saveUser)

                // 4.  Converttirmos respuesta Domain a DTO y respondemos CREATED
                .map(userRestMapper::toResponse)
                .doOnNext(response -> log.info("Usuario creado exitosamente con ID: {}", response.id()))
                .flatMap(userResponse -> ServerResponse
                        .created(URI.create("/api/users/" + userResponse.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse)

                );
    }

    public Mono<ServerResponse> existsByDni(ServerRequest serverRequest) {
        String dni = serverRequest.pathVariable("dni");
        log.info("Consulta de existencia de usuario con DNI: {}", dni);

        return userInputPort.existsByDni(dni)
                .flatMap(exists -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(exists));
    }
}
