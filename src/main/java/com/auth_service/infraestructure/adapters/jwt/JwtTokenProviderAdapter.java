package com.auth_service.infraestructure.adapters.jwt;

import com.auth_service.application.ports.out.TokenProviderOutPort;
import com.auth_service.domain.model.TokenClaims;
import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class JwtTokenProviderAdapter implements TokenProviderOutPort {

    private final JwtProperties jwtProperties;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }


    public JwtTokenProviderAdapter(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String generateToken(User user) {

        // Calculamos el tiempo de expiración del Token
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getExpiration());

        // Creamos un HashMap con los claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole().name());
        claims.put("dni", user.getDni());


        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    @Override
    public TokenClaims getAllClaimsFromToken(String token) {
        Claims libraryClaims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return TokenClaims.builder()
                .userId(libraryClaims.get("userId", Long.class))
                .email(libraryClaims.getSubject())
                .role(libraryClaims.get("role", String.class))
                .build();
    }

}
