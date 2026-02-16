package com.auth_service.infraestructure.adapters.persistence.mapper;

import com.auth_service.domain.model.User;
import com.auth_service.infraestructure.adapters.persistence.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);

}
