package com.auth_service.application.ports.in;

import com.auth_service.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserInputPort {

    Mono<User> saveUser(User user);

    Mono<Boolean> existsByDni(String dni);

}
