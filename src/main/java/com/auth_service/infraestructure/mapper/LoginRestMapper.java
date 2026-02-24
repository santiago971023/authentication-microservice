package com.auth_service.infraestructure.mapper;

import com.auth_service.application.dto.LoginInput;
import com.auth_service.infraestructure.entryPoints.dto.LoginRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;

@Mapper(componentModel = "spring")
public interface LoginRestMapper {

    @Mapping(source = "email", target = "username")
    LoginInput toInput(LoginRequest request);


}
