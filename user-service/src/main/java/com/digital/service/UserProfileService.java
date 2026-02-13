package com.digital.service;

import com.common_service.response.PagedResponse;
import com.digital.dtos.UserProfileDto;

public interface UserProfileService {
    UserProfileDto createUser(UserProfileDto dto);
    PagedResponse<UserProfileDto> getAll(int pageNumber, int pageSize, String  sortBy, String sortDir);
    UserProfileDto update(Long id, UserProfileDto dto);
    void delete(Long id);
    UserProfileDto getById(Long id);
    UserProfileDto searchByName(String name);
}
