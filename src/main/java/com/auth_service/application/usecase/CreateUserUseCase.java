package com.auth_service.application.usecase;

import com.auth_service.application.ports.in.UserInputPort;
import com.auth_service.application.ports.out.UserRepositoryOutPort;
import com.auth_service.domain.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateUserUseCase implements UserInputPort {

    private final UserRepositoryOutPort userRepositoryOutPort;

    public CreateUserUseCase(UserRepositoryOutPort userRepositoryOutPort) {
        this.userRepositoryOutPort = userRepositoryOutPort;
    }

    @Override
    public Mono<User> saveUser(User user) {
        return userRepositoryOutPort.existsByEmail(user.getEmail()).
                flatMap(exists -> {
                        if(exists) {
                            return Mono.error(new RuntimeException("The email already exists"));
                        }
                        return userRepositoryOutPort.save(user);
        });
    }
}
