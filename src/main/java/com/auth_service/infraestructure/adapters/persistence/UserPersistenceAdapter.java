package com.auth_service.infraestructure.adapters.persistence;

import com.auth_service.application.ports.out.UserRepositoryOutPort;
import com.auth_service.domain.model.User;
import reactor.core.publisher.Mono;

public class UserPersistenceAdapter implements UserRepositoryOutPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<User> saveUser(User user) {
        return null;
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return null;
    }
}
