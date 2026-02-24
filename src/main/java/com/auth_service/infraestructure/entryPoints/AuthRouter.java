package com.auth_service.infraestructure.entryPoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouter {

    private static final String PATH = "/api/v1/users/login";

    @Bean
    public RouterFunction<ServerResponse> router2(AuthHandler authHandler){
        return route(
                POST(PATH)
                        .and(accept(MediaType.APPLICATION_JSON)),
                authHandler::login
        );
    }

}
