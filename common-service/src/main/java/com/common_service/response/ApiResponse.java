package com.common_service.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String path;

    private ApiResponse(boolean success, int status, String message, T data, String path) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    // Success response
    public static <T> ApiResponse<T> success(int status, String message, T data, String path) {
        return new ApiResponse<>(true, status, message, data, path);
    }

    // Error response without data
    public static <T> ApiResponse<T> error(int status, String message, String path) {
        return new ApiResponse<>(false, status, message, null, path);
    }

    // Error response with data (like validation errors)
    public static <T> ApiResponse<T> error(int status, String message, T data, String path) {
        return new ApiResponse<>(false, status, message, data, path);
    }
}
