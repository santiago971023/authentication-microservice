package com.auth_service.infraestructure.entryPoints;

import com.auth_service.application.ports.in.UserInputPort;
import com.auth_service.infraestructure.entryPoints.dto.UserRequestDto;
import com.auth_service.infraestructure.mapper.UserRestMapper;
import com.auth_service.infraestructure.shared.RequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
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
        return serverRequest.bodyToMono(UserRequestDto.class)
                .flatMap(requestValidator::validate)
                .flatMap(userDto -> userInputPort.saveUser(userRestMapper.toDomain(userDto))
                        .flatMap(user -> ServerResponse.ok().bodyValue(userRestMapper.toResponse(user)))
                );
    }
}
