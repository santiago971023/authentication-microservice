package com.auth_service.infraestructure.config;

import com.auth_service.application.ports.out.PasswordEncoderOutPort;
import com.auth_service.infraestructure.adapters.encoder.PasswordEncoderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoderOutPort passwordEncoderOutPort(){
        return new PasswordEncoderAdapter(new BCryptPasswordEncoder());
    }

}
