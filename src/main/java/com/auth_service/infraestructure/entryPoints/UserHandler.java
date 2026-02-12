package com.auth_service.infraestructure.entryPoints;

import com.auth_service.application.ports.in.UserInputPort;
import com.auth_service.infraestructure.dto.UserRequestDto;
import com.auth_service.infraestructure.mapper.UserMapper;
import com.auth_service.infraestructure.shared.RequestValidator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {
    private final UserInputPort userInputPort;
    private final RequestValidator requestValidator;
    private final UserMapper userMapper;

    public UserHandler(UserInputPort userInputPort, RequestValidator requestValidator, UserMapper userMapper) {
        this.userInputPort = userInputPort;
        this.requestValidator = requestValidator;
        this.userMapper = userMapper;
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest){
        return serverRequest.bodyToMono(UserRequestDto.class)
                .flatMap(requestValidator::validate)
                .flatMap(userDto -> userInputPort.saveUser(userMapper.toDomain(userDto))
                        .flatMap(user -> ServerResponse.ok().bodyValue(userMapper.toResponse(user)))
                );
    }
}
