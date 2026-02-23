package com.auth_service.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginInput {

    private String username;   //email
    private String password;
}
