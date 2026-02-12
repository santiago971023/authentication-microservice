package com.auth_service.infraestructure.mapper;

import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.dto.UserRequestDto;
import com.auth_service.infraestructure.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserRequestDto userRequestDto);
    UserResponseDto toResponse(User user);
}
