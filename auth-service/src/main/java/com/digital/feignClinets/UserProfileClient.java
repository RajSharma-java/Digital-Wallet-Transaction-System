package com.digital.feignClinets;

import com.common_service.response.ApiResponse;
import com.digital.dto.feignDtos.UserProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-profile-service", url = "http://localhost:8080/api/usersProfile")
public interface UserProfileClient  {

    @PostMapping("/create")
    ApiResponse<UserProfileDto> createUser(@RequestBody UserProfileDto dto);

}
