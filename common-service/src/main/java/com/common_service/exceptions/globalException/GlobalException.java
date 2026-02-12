package com.common_service.exceptions.globalException;

import com.common_service.exceptions.customeException.ResourceAlreadyExistsException;
import com.common_service.exceptions.customeException.ResourceNotFoundException;
import com.common_service.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalException {

    // Resource not found
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex,
//                                                                      WebRequest request) {
//        ApiResponse<Object> response = ApiResponse.error(
//                HttpStatus.NOT_FOUND.value(),
//                ex.getMessage(),
//                request.getDescription(false)
//        );
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

    // Resource already exists
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceAlreadyExists(ResourceAlreadyExistsException ex,
                                                                           WebRequest request) {
        ApiResponse<Object> response = ApiResponse.error(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex,
                                                                      WebRequest request) {
        ApiResponse<Object> response = ApiResponse.error(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }



    // Handle JPA / entity validation errors
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(ConstraintViolationException ex,
                                                                         WebRequest request) {

        // Collect all violations as field -> message
        Map<String, String> errors = ex.getConstraintViolations()
                .stream()
                .collect(Collectors.toMap(
                        v -> v.getPropertyPath().toString(), // field name
                        v -> v.getMessage()
                ));

        ApiResponse<Object> response = ApiResponse.error(
                400,
                "Validation failed",
                errors,
                request.getDescription(false)
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // All other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex,
                                                                     WebRequest request) {
        ApiResponse<Object> response = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
