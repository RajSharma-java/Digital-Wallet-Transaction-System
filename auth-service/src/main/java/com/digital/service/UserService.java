package com.digital.service;

import com.common_service.response.PagedResponse;
import com.digital.dto.UserDto;

public interface UserService {

     UserDto registerUser(UserDto userDto);

     PagedResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

     UserDto getUserById(Long id);

     UserDto updateUser(Long id, UserDto userDto);

     void deleteUser(Long id);


}
