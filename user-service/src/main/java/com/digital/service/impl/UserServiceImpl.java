package com.digital.service.impl;

import com.common_service.exceptions.customeException.ResourceAlreadyExistsException;
import com.common_service.exceptions.customeException.ResourceNotFoundException;
import com.common_service.response.PagedResponse;
import com.digital.conifg.UserMapper;
import com.digital.dtos.UserDto;
import com.digital.model.User;
import com.digital.repo.UserRepo;
import com.digital.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepo userRepo;

    private UserServiceImpl(UserMapper userMapper, UserRepo userRepo) {
        this.userMapper = userMapper;
        this.userRepo = userRepo;
    }

    @Override
    public UserDto createUser(UserDto dto) {

        Optional<User> phone = userRepo.findByPhone(dto.getPhone());
        if(phone.isPresent()){
            throw  new ResourceAlreadyExistsException("Phone number exists, try other number");
        }
        Optional<User> email = userRepo.findByEmail(dto.getEmail());
        if(email.isPresent()) throw new ResourceAlreadyExistsException("Email Already exists, try other email id;");
        User users = userMapper.toEntity(dto);
        User savedUser = userRepo.save(users);
        return userMapper.toDto(savedUser);
    }

    @Override
    public PagedResponse<UserDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> userPage = userRepo.getActiveUsers(pageable);
        PagedResponse<UserDto> response = PagedResponse.fromPage(userPage, userMapper::toDto);

        return response;

    }

    @Override
    public UserDto update(Long id, UserDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setDeleted(true);
        userRepo.save(user);
    }

    @Override
    public UserDto getById(Long id) {
        return null;
    }

    @Override
    public UserDto searchByName(String name) {
        return null;
    }
}
