package com.digital.service.impl;

import com.common_service.exceptions.customeException.ResourceAlreadyExistsException;
import com.common_service.response.ApiResponse;
import com.common_service.response.PagedResponse;
import com.digital.config.UserMapper;
import com.digital.dto.UserDto;
import com.digital.dto.feignDtos.UserProfileDto;
import com.digital.feignClinets.UserProfileClient;
import com.digital.model.User;
import com.digital.repo.UserRepo;
import com.digital.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl  implements UserService {

@Autowired
    private  UserMapper userMapper;
    private  final UserRepo userRepo;
    private final UserProfileClient userProfileClient;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserMapper userMapper, UserRepo userRepo, @Lazy UserProfileClient userProfileClient, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.userRepo = userRepo;
        this.userProfileClient = userProfileClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        // 1️⃣ Check if email exists
        Optional<User> email = userRepo.findByEmail(userDto.getEmail());
        if(email.isPresent()){
            throw new ResourceAlreadyExistsException("Email already exists, try other email id");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //  Save user locally
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepo.save(user);
        UserDto savedUserDto = userMapper.toDto(savedUser);

        // 3 Call user-profile-service via Feign to create profile
        UserProfileDto profileDto = new UserProfileDto();
        profileDto.setUserId(savedUserDto.getId());
        profileDto.setFullName(savedUserDto.getFullName());
        profileDto.setEmail(savedUserDto.getEmail());

        ApiResponse<UserProfileDto> response = userProfileClient.createUser(profileDto);

        // Optionally, you can check response status and log or handle failures
        if(response == null || response.getData() == null){
            // handle failure, maybe rollback or log warning
            System.out.println("Failed to create profile in user-profile-service");
        }

        return savedUserDto;
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
