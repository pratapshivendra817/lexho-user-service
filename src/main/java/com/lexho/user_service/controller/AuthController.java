package com.lexho.user_service.controller;

import com.lexho.user_service.dto.OtpRequestDto;
import com.lexho.user_service.dto.OtpVerifyDto;
import com.lexho.user_service.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestBody OtpRequestDto request) {
        return authService.sendOtp(request.getPhone());
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpVerifyDto request) {
        return authService.verifyOtp(request.getPhone(), request.getOtp());
    }
}