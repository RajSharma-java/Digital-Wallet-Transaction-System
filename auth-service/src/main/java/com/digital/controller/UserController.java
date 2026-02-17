package com.digital.controller;

import com.common_service.response.ApiResponse;
import com.common_service.response.PagedResponse;
import com.digital.dto.UserDto;
import com.digital.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {
    
    public  final UserService userService;

    public UserController(@Lazy  UserService userService) {
        this.userService = userService;
    }


    // create
    @PostMapping("register")
    public ResponseEntity<ApiResponse<UserDto>> createUser(@Valid @RequestBody UserDto dto, HttpServletRequest request) {
        UserDto savedUser = userService.registerUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        201,
                        "User created successfully",
                        savedUser,
                        request.getRequestURI()
                ));
    }
    @GetMapping("/allUser")
    public ResponseEntity<PagedResponse<UserDto>> getAllUserWithPagination(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            HttpServletRequest request) {

        PagedResponse<UserDto> allUsers =
                userService.getAllUser(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.ok(allUsers);
    }


}
