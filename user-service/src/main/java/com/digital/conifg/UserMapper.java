package com.digital.conifg;

import com.digital.dtos.UserProfileDto;
import com.digital.model.UserProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileDto toDto(UserProfile userProfile);

    UserProfile toEntity(UserProfileDto dto);
}
