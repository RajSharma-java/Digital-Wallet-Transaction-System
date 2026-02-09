package com.digital.service;

import com.common_service.response.PagedResponse;
import com.digital.dtos.UserDto;

public interface UserService {
    UserDto createUser(UserDto dto);
    PagedResponse<UserDto> getAll(int pageNumber, int pageSize, String  sortBy, String sortDir);
    UserDto update( Long id,UserDto dto);
    void delete(Long id);
    UserDto getById(Long id);
    UserDto searchByName(String name);
}
