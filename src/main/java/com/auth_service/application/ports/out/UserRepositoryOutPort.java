package com.auth_service.application.ports.out;

import com.auth_service.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserRepositoryOutPort {

    Mono<User> saveUser(User user);
    Mono<Boolean> existsByEmail(String email);

}
