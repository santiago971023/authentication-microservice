package com.auth_service.application.ports.out;

import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.adapters.persistence.UserEntity;
import org.mapstruct.control.MappingControl;
import reactor.core.publisher.Mono;

public interface UserRepositoryOutPort {

    Mono<User> save(User user);
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByDni(String dni);
    Mono<User> findByEmail(String email);
    Mono<User> findByDni(String dni);

}
