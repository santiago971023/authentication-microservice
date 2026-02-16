package com.auth_service.infraestructure.mapper;

import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.entryPoints.dto.UserRequestDto;
import com.auth_service.infraestructure.entryPoints.dto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {

    User toDomain(UserRequestDto userRequestDto);
    UserResponseDto toResponse(User user);


}
