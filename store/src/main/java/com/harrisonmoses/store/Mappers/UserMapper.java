package com.harrisonmoses.store.Mappers;

import com.harrisonmoses.store.Dtos.CreateUserRequest;
import com.harrisonmoses.store.Dtos.UpdateUserRequest;
import com.harrisonmoses.store.Dtos.UserDto;
import com.harrisonmoses.store.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target ="createdAt" ,expression ="java(java.time.LocalDateTime.now())" )
    UserDto toDto(User user);

    User toEntity(CreateUserRequest user);

    void updateUser( UpdateUserRequest request, @MappingTarget User user);
}
