package com.auth_service.application.usecase;

import com.auth_service.application.ports.in.UserInputPort;
import com.auth_service.application.ports.out.PasswordEncoderOutPort;
import com.auth_service.application.ports.out.UserRepositoryOutPort;
import com.auth_service.domain.exception.UserAlreadyExistsException;
import com.auth_service.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CreateUserUseCase implements UserInputPort {

    private final UserRepositoryOutPort userRepositoryOutPort;
    private final PasswordEncoderOutPort passwordEncoderOutPort;

    public CreateUserUseCase(UserRepositoryOutPort userRepositoryOutPort, PasswordEncoderOutPort passwordEncoderOutPort) {
        this.userRepositoryOutPort = userRepositoryOutPort;
        this.passwordEncoderOutPort = passwordEncoderOutPort;
    }

    @Override
    @Transactional
    public Mono<User> saveUser(User user) {
        log.debug("Iniciando validación de negocio para usuario: {}", user.getDni());
        return userRepositoryOutPort.existsByDni(user.getDni())
                .flatMap(dniExists -> {
                    if (dniExists) {
                        log.warn("Intento de registro fallido: DNI {} ya existe", user.getDni());
                        return Mono.error(new UserAlreadyExistsException("The DNI already exists"));
                    }
                    return userRepositoryOutPort.existsByEmail(user.getEmail());
                })
                .flatMap(emailExists -> {
                    if (emailExists) {
                        log.warn("Intento de registro fallido: Email {} ya existe", user.getEmail());
                        return Mono.error(new UserAlreadyExistsException("The email already exists"));
                    } else {
                        String encodedPassword = passwordEncoderOutPort.encode(user.getPassword());
                        user.setPassword(encodedPassword);
                    }
                    return userRepositoryOutPort.save(user)
                            .doOnSuccess(u -> log.info("Usuario persistido correctamente en base de datos. ID: {}", u.getId()));
                });
    }

    @Override
    public Mono<Boolean> existsByDni(String dni) {
        log.debug("Verificando existencia de usuario con DNI: {}", dni);
        return userRepositoryOutPort.existsByDni(dni)
                .doOnSuccess(exists -> log.debug("DNI {} existe: {}", dni, exists));
    }
}
