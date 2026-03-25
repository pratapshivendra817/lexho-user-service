package com.lexho.user_service.exception;

import com.lexho.user_service.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 🔹 NOT FOUND
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFound(ResourceNotFoundException ex) {

        log.error("Resource not found: {}", ex.getMessage());

        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null),
                HttpStatus.NOT_FOUND
        );
    }

    // 🔹 BAD REQUEST
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(BadRequestException ex) {

        log.error("Bad request: {}", ex.getMessage());

        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    // 🔹 GENERIC ERROR
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobal(Exception ex) {

        ex.printStackTrace(); // 🔥 ADD THIS

        return new ResponseEntity<>(
                new ApiResponse<>(false, ex.getMessage(), null),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}