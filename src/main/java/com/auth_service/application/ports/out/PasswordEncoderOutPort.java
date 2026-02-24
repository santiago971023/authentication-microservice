package com.auth_service.application.ports.out;

public interface PasswordEncoderOutPort {

    boolean matches(String rawPassword, String encodedPassword);

    String encode(String rawPassword);

}
