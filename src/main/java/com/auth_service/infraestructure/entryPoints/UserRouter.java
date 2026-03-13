package com.auth_service.infraestructure.entryPoints;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class UserRouter {

    private static final String PATH = "/api/v1/users";

    @Bean
    public RouterFunction<ServerResponse> router(UserHandler userHandler){
        return route(
                POST(PATH)
                        .and(accept(MediaType.APPLICATION_JSON)),
                userHandler::createUser
        ).andRoute(
                GET(PATH + "/exists/{dni}"),
                userHandler::existsByDni
        ).andRoute(
                GET(PATH + "/{dni}"),
                userHandler::findUserByDni
        )

                ;
    }

}
