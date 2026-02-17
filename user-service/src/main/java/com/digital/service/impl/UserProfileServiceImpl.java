package com.digital.service.impl;

import com.common_service.exceptions.customeException.ResourceAlreadyExistsException;
import com.common_service.exceptions.customeException.ResourceNotFoundException;
import com.common_service.response.PagedResponse;
import com.digital.Helper.ReferralCodeGenerator;
import com.digital.conifg.UserMapper;
import com.digital.dtos.UserProfileDto;
import com.digital.model.UserProfile;
import com.digital.repo.UserRepo;
import com.digital.service.UserProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserMapper userMapper;
    private final UserRepo userRepo;

    private UserProfileServiceImpl(UserMapper userMapper, UserRepo userRepo) {
        this.userMapper = userMapper;
        this.userRepo = userRepo;
    }

    @Override
    public UserProfileDto createUser(UserProfileDto dto) {


        Optional<UserProfile> email = userRepo.findByEmail(dto.getEmail());
        if(email.isPresent()) throw new ResourceAlreadyExistsException("Email Already exists, try other email id;");
        UserProfile users = userMapper.toEntity(dto);
        users.setReferralCode(ReferralCodeGenerator.generateReferralCode());
        UserProfile savedUserProfile = userRepo.save(users);
        return userMapper.toDto(savedUserProfile);
    }

    @Override
    public PagedResponse<UserProfileDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<UserProfile> userPage = userRepo.getActiveUsers(pageable);
        PagedResponse<UserProfileDto> response = PagedResponse.fromPage(userPage, userMapper::toDto);
        return response;

    }



    @Override
    public UserProfileDto update(Long id, UserProfileDto dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        UserProfile userProfile = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userProfile.setDeleted(true);
        userRepo.save(userProfile);
    }

    @Override
    public UserProfileDto getById(Long id) {
        return null;
    }

    @Override
    public UserProfileDto searchByName(String name) {
        return null;
    }
}
