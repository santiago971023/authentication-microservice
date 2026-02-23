package com.auth_service.application.ports.out;

import com.auth_service.domain.model.User;

public interface TokenProviderOutPort {

    String generateToken(User user);


}
