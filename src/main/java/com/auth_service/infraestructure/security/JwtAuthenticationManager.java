package com.auth_service.infraestructure.security;

import com.auth_service.application.ports.out.TokenProviderOutPort;
import com.auth_service.domain.model.TokenClaims;
import io.netty.util.concurrent.AbstractEventExecutor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenProviderOutPort tokenProviderOutPort;

    public JwtAuthenticationManager(TokenProviderOutPort tokenProviderOutPort) {
        this.tokenProviderOutPort = tokenProviderOutPort;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {

        return Mono.just(authentication.getCredentials().toString())
                .map(token -> {
                    TokenClaims claims = tokenProviderOutPort.getAllClaimsFromToken(token);
                    String email = claims.getEmail();
                    String role = claims.getRole();
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
                    return (Authentication) new UsernamePasswordAuthenticationToken(email, null, List.of(authority));
                })
                .onErrorResume(e -> Mono.empty());
    }



}
