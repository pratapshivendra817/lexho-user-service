package com.lexho.user_service.service;

import com.lexho.user_service.entity.Otp;
import com.lexho.user_service.entity.User;
import com.lexho.user_service.repository.OtpRepository;
import com.lexho.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

        private final OtpService otpService;
        private final UserRepository userRepository;

        public AuthService(OtpService otpService, UserRepository userRepository) {
            this.otpService = otpService;
            this.userRepository = userRepository;
        }

        // 🔹 Send OTP
        public String sendOtp(String phone) {
            return otpService.generateOtp(phone); // 🔥 delegate
        }

        // 🔹 Verify OTP
        public String verifyOtp(String phone, String otpValue) {

            boolean isValid = otpService.verifyOtp(phone, otpValue);

            if (!isValid) {
                return "Invalid or Expired OTP";
            }

            // Check user exist
            return userRepository.findByPhone(phone)
                    .map(user -> "Login Successful")
                    .orElseGet(() -> {
                        User newUser = new User();
                        newUser.setPhone(phone);
                        newUser.setRole("USER");

                        userRepository.save(newUser);
                        return "User Registered & Login Successful";
                    });
        }
    }

