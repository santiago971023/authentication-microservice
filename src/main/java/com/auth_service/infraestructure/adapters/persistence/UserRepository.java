package com.auth_service.infraestructure.adapters.persistence;

import com.auth_service.domain.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<Boolean> existsByEmail(String email);
    Mono<Boolean> existsByDni(String dni);
    Mono<UserEntity> findByEmail(String email);
}
