package com.digital.conifg;

import com.digital.dtos.UserDto;
import com.digital.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}
