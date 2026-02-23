package com.auth_service.infraestructure.adapters.jwt;

import com.auth_service.application.ports.out.TokenProviderOutPort;
import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.config.JwtProperties;
import org.springframework.stereotype.Component;


@Component
public class JwtTokenProviderAdapter implements TokenProviderOutPort {

    private final JwtProperties jwtProperties;

    public JwtTokenProviderAdapter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String generateToken(User user) {
        return "";
    }

}
