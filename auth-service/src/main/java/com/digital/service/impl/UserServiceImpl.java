package com.digital.service.impl;

import com.common_service.exceptions.customeException.ResourceAlreadyExistsException;
import com.common_service.response.PagedResponse;
import com.digital.config.UserMapper;
import com.digital.dto.UserDto;
import com.digital.model.User;
import com.digital.repo.UserRepo;
import com.digital.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {

    private final UserService userService;

    private final UserMapper userMapper;

    private  final UserRepo userRepo;

    public UserServiceImpl(UserService userService, UserMapper userMapper, UserRepo userRepo) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userRepo = userRepo;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        Optional<User> email = userRepo.findByEmail(userDto.getEmail());
        if(email.isPresent()){
            throw new ResourceAlreadyExistsException("Email already exists, try other email id");
        }
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepo.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public PagedResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> userPage = userRepo.getActiveUsers(pageable);
        PagedResponse<UserDto> response = PagedResponse.fromPage(userPage, userMapper::toDto);
        return response;
    }

    @Override
    public UserDto getUserById(Long id) {
        return null;
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
