package com.auth_service.infraestructure.adapters.persistence;

import com.auth_service.application.ports.out.UserRepositoryOutPort;
import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.adapters.persistence.mapper.UserPersistenceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserPersistenceAdapter implements UserRepositoryOutPort {

    private final UserRepository userRepository;
    private final UserPersistenceMapper persistenceMapper;

    public UserPersistenceAdapter(UserRepository userRepository, UserPersistenceMapper persistenceMapper) {
        this.userRepository = userRepository;
        this.persistenceMapper = persistenceMapper;
    }

    @Override
    public Mono<User> save(User user) {

        log.trace("Guardando entidad UserEntity en DB: {}", user.getEmail());
        // 1. Aquí tenemos que convertir el User que recibimos en entiddad de BD
        UserEntity entity = persistenceMapper.toEntity(user);

        // 2. Aquí lo guardamos
        return userRepository.save(entity)
                .map(persistenceMapper::toDomain)
                .doOnError(e -> log.error("Error crítico conectando con base de datos al guardar usuario.", e));
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByDni(String dni) {
        return userRepository.existsByDni(dni);
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Mono<User> findByDni(String dni) {
        return userRepository.findByDni(dni)
                .map(persistenceMapper::toDomain);
    }
}
