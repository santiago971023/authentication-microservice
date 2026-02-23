package com.auth_service.application.usecase;

import com.auth_service.application.dto.LoginInput;
import com.auth_service.application.ports.in.LoginInputPort;
import com.auth_service.application.ports.out.PasswordEncoderOutPort;
import com.auth_service.application.ports.out.TokenProviderOutPort;
import com.auth_service.application.ports.out.UserRepositoryOutPort;
import com.auth_service.domain.exception.ErrorMessageBusiness;
import com.auth_service.domain.exception.InvalidCredentialsException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class LoginUseCase implements LoginInputPort {

    private final UserRepositoryOutPort userRepositoryOutPort;
    private final PasswordEncoderOutPort passwordEncoderOutPort;
    private final TokenProviderOutPort tokenProviderOutPort;

    public LoginUseCase(UserRepositoryOutPort userRepositoryOutPort, PasswordEncoderOutPort passwordEncoderOutPort, TokenProviderOutPort tokenProviderOutPort) {
        this.userRepositoryOutPort = userRepositoryOutPort;
        this.passwordEncoderOutPort = passwordEncoderOutPort;
        this.tokenProviderOutPort = tokenProviderOutPort;
    }

    @Override
    public Mono<String> login(LoginInput loginInput) {
        return userRepositoryOutPort.findByEmail(loginInput.getUsername())
                .switchIfEmpty(Mono.error(new InvalidCredentialsException(ErrorMessageBusiness.INVALID_CREDENTIALS_EXCEPTION.getMessage())))
                .flatMap(user -> {
                    log.info("Usuario encontrado, se procede a verificar contraseña.");
                    if(passwordEncoderOutPort.matches(loginInput.getPassword(), user.getPassword())){
                        log.info("La contraseña es correcta, se procede a generar Token.");
                        String token = tokenProviderOutPort.generateToken(user);
                        return Mono.just(token);
                    }else{
                        log.info("La contraseña es incorrecta.");
                        return Mono.error(new InvalidCredentialsException(ErrorMessageBusiness.INVALID_CREDENTIALS_EXCEPTION.getMessage()));
                    }
                });
    }
}
