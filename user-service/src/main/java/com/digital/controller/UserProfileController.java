package com.digital.controller;

import com.common_service.response.ApiResponse;
import com.common_service.response.PagedResponse;
import com.digital.dtos.UserProfileDto;
import com.digital.service.UserProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/usersProfile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // create
    @PostMapping("create")
    public ResponseEntity<ApiResponse<UserProfileDto>> createUser(@Valid @RequestBody UserProfileDto dto, HttpServletRequest request) {
        UserProfileDto savedUser = userProfileService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        201,
                        "User created successfully",
                        savedUser,
                        request.getRequestURI()
                ));
    }
    @GetMapping("/allUser")
    public ResponseEntity<PagedResponse<UserProfileDto>> getAllUserWithPagination(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            HttpServletRequest request) {

        PagedResponse<UserProfileDto> allUsers =
                userProfileService.getAll(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.ok(allUsers);
    }


    // here we will delete the soft user
    @PutMapping("/deleteUser/soft-delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id, HttpServletRequest request){
        userProfileService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(200,
                        "User Deleted successfully",
                        null,
                        request.getRequestURI()

                ));
    }






}
