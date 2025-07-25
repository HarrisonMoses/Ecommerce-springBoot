package com.harrisonmoses.store.Mappers;

import com.harrisonmoses.store.Dtos.UserDto;
import com.harrisonmoses.store.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
