package com.auth_service.infraestructure.adapters.persistence;

import com.auth_service.application.ports.out.UserRepositoryOutPort;
import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.adapters.persistence.mapper.UserPersistenceMapper;
import com.auth_service.infraestructure.mapper.UserRestMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserPersistenceAdapter implements UserRepositoryOutPort {

    private final UserRepository userRepository;
    private final UserPersistenceMapper persistenceMapper;

    public UserPersistenceAdapter(UserRepository userRepository, UserPersistenceMapper persistenceMapper) {
        this.userRepository = userRepository;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public Mono<User> save(User user) {

        // 1. Aquí tenemos que convertir el User que recibimos en entiddad de BD
        UserEntity entity = persistenceMapper.toEntity(user);

        // 2. Aquí lo guardamos
        return userRepository.save(entity)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
