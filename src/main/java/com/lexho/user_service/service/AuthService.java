package com.lexho.user_service.service;

import com.lexho.user_service.config.JwtUtil;
import com.lexho.user_service.entity.User;
import com.lexho.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final OtpService otpService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // ✅ FIXED constructor
    public AuthService(OtpService otpService,
                       UserRepository userRepository,
                       JwtUtil jwtUtil) {
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // 🔹 Send OTP
    public String sendOtp(String phone) {
        return otpService.generateOtp(phone);
    }

    // 🔹 Verify OTP + JWT
    public String verifyOtp(String phone, String otpValue) {

        boolean isValid = otpService.verifyOtp(phone, otpValue);

        if (!isValid) {
            return "Invalid or Expired OTP";
        }

        // 🔥 User find / create
        User user = userRepository.findByPhone(phone)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setPhone(phone);
                    newUser.setRole("USER");
                    return userRepository.save(newUser);
                });

        // 🔥 Token generate (correct place)
        String token = jwtUtil.generateToken(user.getPhone());

        return "Login Successful | Token: " + token;
    }
}