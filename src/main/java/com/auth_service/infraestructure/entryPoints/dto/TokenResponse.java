package com.auth_service.infraestructure.entryPoints.dto;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TokenResponse {

    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }
}
