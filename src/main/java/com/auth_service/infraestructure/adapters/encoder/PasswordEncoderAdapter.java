package com.auth_service.infraestructure.adapters.encoder;

import com.auth_service.application.ports.out.PasswordEncoderOutPort;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PasswordEncoderAdapter implements PasswordEncoderOutPort {

    private final PasswordEncoder encoder;

    public PasswordEncoderAdapter(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
