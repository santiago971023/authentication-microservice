package com.auth_service.application.ports.in;

import com.auth_service.application.dto.LoginInput;
import reactor.core.publisher.Mono;

public interface LoginInputPort {

    public Mono<String> login(LoginInput loginInput);

}
