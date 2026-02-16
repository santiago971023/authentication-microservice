package com.auth_service.application.usecase;

import com.auth_service.application.ports.in.UserInputPort;
import com.auth_service.application.ports.out.UserRepositoryOutPort;
import com.auth_service.domain.exception.UserAlreadyExistsException;
import com.auth_service.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CreateUserUseCase implements UserInputPort {

    private final UserRepositoryOutPort userRepositoryOutPort;

    public CreateUserUseCase(UserRepositoryOutPort userRepositoryOutPort) {
        this.userRepositoryOutPort = userRepositoryOutPort;
    }

    @Override
    public Mono<User> saveUser(User user) {
        log.debug("Iniciando validación de negocio para usuario: {}", user.getEmail());

        return userRepositoryOutPort.existsByEmail(user.getEmail()).
                flatMap(exists -> {
                        if(exists) {
                            log.warn("Intento de registro fallido: Email {} ya existe", user.getEmail());
                            return Mono.error(new UserAlreadyExistsException("The email already exists"));
                        }

                        // Aquí debemos hashear la contraseña.

                        return userRepositoryOutPort.save(user)
                                .doOnSuccess(u -> log.info("Usuario persistido correctamente en base de datos. ID: {}", u.getId()));
        });
    }
}
